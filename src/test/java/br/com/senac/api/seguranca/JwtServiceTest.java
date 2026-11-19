package br.com.senac.api.seguranca;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private static final String SEGREDO = "segredo-de-teste-com-pelo-menos-32-caracteres!!";

    private final JwtService jwtService = new JwtService(SEGREDO, 60_000L);

    @Test
    void tokenGeradoDevolveOLoginNaValidacao() {
        String token = jwtService.gerarToken("julia");

        assertEquals("julia", jwtService.validarToken(token));
    }

    @Test
    void rejeitaTokenAssinadoComOutroSegredo() {
        JwtService outroServico = new JwtService("outro-segredo-com-pelo-menos-32-caracteres-xx", 60_000L);
        String token = outroServico.gerarToken("julia");

        assertThrows(JwtException.class, () -> jwtService.validarToken(token));
    }

    @Test
    void rejeitaTokenExpirado() {
        JwtService servicoExpirado = new JwtService(SEGREDO, -1_000L);
        String token = servicoExpirado.gerarToken("julia");

        assertThrows(ExpiredJwtException.class, () -> jwtService.validarToken(token));
    }
}
