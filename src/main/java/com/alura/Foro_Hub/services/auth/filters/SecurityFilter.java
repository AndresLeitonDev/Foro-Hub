package com.alura.Foro_Hub.services.auth.filters;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alura.Foro_Hub.models.Usuario;
import com.alura.Foro_Hub.services.UsuarioService;
import com.alura.Foro_Hub.services.auth.TokenJWTConfig;
import com.alura.Foro_Hub.services.auth.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(TokenJWTConfig.HEADER_AUTHORIZATION);
        if (header != null && header.startsWith(TokenJWTConfig.PREFIX_TOKEN)) {
            String token = header.replace(TokenJWTConfig.PREFIX_TOKEN, "");
            if (token == null || token.isEmpty()) {
                System.out.println("Token inválido");
            } else {
                System.out.println(token);
                String subject = tokenService.getSubject(token);
                if (subject != null && !subject.isEmpty()) {
                    UserDetails usuario = usuarioService.findByNombreUD(subject);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                            usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
