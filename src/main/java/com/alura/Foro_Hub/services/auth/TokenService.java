package com.alura.Foro_Hub.services.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.alura.Foro_Hub.models.Usuario;

@Service
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String jwtSecret;

    public String generateToken(Usuario usuario) {
        
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                .withIssuer("auth0")
                .withSubject(usuario.getNombre())
                .withClaim("id", usuario.getId())
                .withExpiresAt(getExpirationTime())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error al crear el token");
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token) {
        String subject = "";
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            DecodedJWT verifier = JWT.require(algorithm).withIssuer("auth0").build().verify(token);
            subject = verifier.getSubject();
        }catch(JWTVerificationException e){
            System.out.println(e.getMessage());
        }
        return subject.equals("") ? "Token inválido" : subject;
    }
}
