package br.com.alurafood.controller;

import br.com.alurafood.dto.PagamentoDto;
import br.com.alurafood.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10)Pageable paginacao){
        return service.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id){
        PagamentoDto dto = service.obterPorId(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto,
                                                  UriComponentsBuilder uriBuider){
        PagamentoDto pagamentoDto = service.criarPagamento(dto);

        URI endereco = uriBuider.path("/pagamentos/{id}").buildAndExpand(pagamentoDto.getId()).toUri();

        return ResponseEntity.created(endereco).body(pagamentoDto);
    }

    @PutMapping
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto dto){

        PagamentoDto pagamentoDto = service.atualizarPagamento(id, dto);

        return ResponseEntity.ok(pagamentoDto);
    }

    @DeleteMapping
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){
        service.excluirPagamento(id);

        return ResponseEntity.noContent().build();
    }
}
