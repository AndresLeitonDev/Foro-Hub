package com.alura.Foro_Hub.services.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.alura.Foro_Hub.services.auth.filters.SecurityFilter;

@Configuration
public class SecurityConfigurations {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authRules -> authRules

                /* PERMISOS */
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/curso").permitAll()
                .requestMatchers(HttpMethod.GET, "/curso/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/perfil").permitAll()
                .requestMatchers(HttpMethod.GET, "/perfil/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/usuario").permitAll()
                .requestMatchers(HttpMethod.GET, "/usuario/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/topico").permitAll()
                .requestMatchers(HttpMethod.GET, "/topico/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/topico/curso/{curso}").permitAll()
                .requestMatchers(HttpMethod.GET, "/topico/anio/{anio}").permitAll()
                .requestMatchers(HttpMethod.GET, "/topico/order").permitAll()
                .requestMatchers(HttpMethod.GET, "/respuesta").permitAll()
                .requestMatchers(HttpMethod.GET, "/respuesta/{id}").permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
