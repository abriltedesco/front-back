package com.example.demo.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // lee la clave secreta del application.properties
    @Value("${jwt.secret}")
    private String secret; 

    @Value("${jwt.expiration}")
    private long expiration;

public String generarToken(String mail, String tipo, int dni) {
    return Jwts.builder()
            .subject(mail)
            .claim("tipo", tipo)
            .claim("dni", dni)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getKey())
            .compact();
}

    // extrae el mail (subject) del token
    public String extraerMail(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    // valida que el token corresponda al mail dado y no haya expirado
    // cuando llega un token, verifica la firma y que no haya expirado
    public boolean validarToken(String token, String mail) {
        return extraerMail(token).equals(mail) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
