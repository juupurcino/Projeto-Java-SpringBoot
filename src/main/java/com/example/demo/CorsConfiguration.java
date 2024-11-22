package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        // registry.addMapping("/**")  // ** significa todas as rotas
        .allowedOrigins("http://127.0.0.1:5500")  
        .allowedHeaders("*")  // Permite todos os cabeçalhos
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
}
