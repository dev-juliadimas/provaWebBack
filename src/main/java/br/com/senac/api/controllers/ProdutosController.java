package br.com.senac.api.controllers;

import br.com.senac.api.dtos.ProdutosRequestDto;
import br.com.senac.api.entidades.Produtos;
import br.com.senac.api.services.ProdutosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;

    public ProdutosController(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    @GetMapping("/listar")
    public List<Produtos> listarTodos() {
        return produtosService.listarTodos();
    }

    @PostMapping("/criar")
    public ResponseEntity<Produtos> criar(@RequestBody ProdutosRequestDto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosService.criar(produto));
    }

    @PutMapping("/atualizar/{id}")
    public Produtos atualizar(@PathVariable("id") Long id, @RequestBody ProdutosRequestDto produto) {
        return produtosService.atualizar(id, produto);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        produtosService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
