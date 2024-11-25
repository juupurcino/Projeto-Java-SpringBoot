package com.example.demo;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.dto.Token;
import com.example.demo.filters.JWTAuthenticationFilter;
import com.example.demo.services.JWTService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    JWTService<Token> jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Adiciona suporte a CORS
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user").permitAll()
                .requestMatchers("/auth").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JWTAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
            .cors(config -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("http://localhost:5500");
                configuration.addAllowedOrigin("http://127.0.0.1:5500");
                configuration.addAllowedHeader("Authorization");
                configuration.addAllowedHeader("Content-Type");
                configuration.addAllowedMethod("GET");
                configuration.addAllowedMethod("POST");
                configuration.addAllowedMethod("PUT");
                configuration.addAllowedMethod("DELETE");
                configuration.setAllowCredentials(true);
        
                // Registro da configuração CORS
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                config.configurationSource(source);
            })
            .build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // Origem do frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization")); // Expõe o cabeçalho Authorization
        configuration.setAllowCredentials(true); // Permite envio de cookies e credenciais
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}