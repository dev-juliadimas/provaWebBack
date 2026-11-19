package br.com.senac.api.controllers;

import br.com.senac.api.dtos.MarcasRequestDto;
import br.com.senac.api.entidades.Marcas;
import br.com.senac.api.services.MarcasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcasController {

    private final MarcasService marcasService;

    public MarcasController(MarcasService marcasService) {
        this.marcasService = marcasService;
    }

    @GetMapping("/listar")
    public List<Marcas> listarTodos() {
        return marcasService.listarTodos();
    }

    @PostMapping("/criar")
    public ResponseEntity<Marcas> criar(@RequestBody MarcasRequestDto marca) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcasService.criar(marca));
    }

    @PutMapping("/atualizar/{id}")
    public Marcas atualizar(@PathVariable("id") Long id, @RequestBody MarcasRequestDto marca) {
        return marcasService.atualizar(id, marca);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        marcasService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
