package br.com.senac.api.controllers;

import br.com.senac.api.dtos.UsuariosRequestDto;
import br.com.senac.api.entidades.Usuarios;
import br.com.senac.api.services.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/listar")
    public List<Map<String, Object>> listarTodos() {
        return usuariosService.listarTodos().stream()
                .map(this::paraResposta)
                .toList();
    }

    @PostMapping("/criar")
    public ResponseEntity<Map<String, Object>> criar(@RequestBody UsuariosRequestDto usuario) {
        Usuarios criado = usuariosService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(paraResposta(criado));
    }

    @PutMapping("/atualizar/{id}")
    public Map<String, Object> atualizar(@PathVariable("id") Long id, @RequestBody UsuariosRequestDto usuario) {
        return paraResposta(usuariosService.atualizar(id, usuario));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        usuariosService.deletar(id);
        return ResponseEntity.ok().build();
    }

    // não devolve a senha (hash) em nenhuma resposta
    private Map<String, Object> paraResposta(Usuarios u) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("id", u.getId());
        corpo.put("login", u.getLogin());
        return corpo;
    }
}