package br.com.senac.api.controllers;

import br.com.senac.api.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarErroDeNegocio(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(ResponseUtil.parserMensagem(e.getMessage()));
    }
}
