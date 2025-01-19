package com.alura.Foro_Hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.Foro_Hub.models.Usuario;
import com.alura.Foro_Hub.models.dto.UsuarioDto;
import com.alura.Foro_Hub.services.auth.JWTTokenDto;
import com.alura.Foro_Hub.services.auth.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CentralMethods centralMethods;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) {

        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(usuarioDto.nombre(), usuarioDto.password());
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            String jwToken = tokenService.generateToken((Usuario)usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new JWTTokenDto(jwToken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
        }
        
    }

}
