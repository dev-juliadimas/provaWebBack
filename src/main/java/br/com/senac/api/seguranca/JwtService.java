package br.com.senac.api.seguranca;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey chave;
    private final JwtParser parser;
    private final long expirationMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long expirationMs) {
        this.chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parser().verifyWith(chave).build();
        this.expirationMs = expirationMs;
    }

    public String gerarToken(String login) {
        Date agora = new Date();
        return Jwts.builder()
                .subject(login)
                .issuedAt(agora)
                .expiration(new Date(agora.getTime() + expirationMs))
                .signWith(chave)
                .compact();
    }

    public String validarToken(String token) {
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }
}
