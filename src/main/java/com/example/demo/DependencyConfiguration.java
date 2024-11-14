package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.dto.Token;
import com.example.demo.impl.JwtImplementation;
import com.example.demo.impl.QuestionImplementation;
import com.example.demo.impl.SpaceImplementation;
import com.example.demo.services.JWTService;
import com.example.demo.services.QuestionService;
import com.example.demo.services.SpaceService;




@Configuration
public class DependencyConfiguration {
    @Bean
    @Scope("singleton")
    public SpaceService spaceService(){
        return new SpaceImplementation();
    }

    @Bean
    @Scope("singleton")
    public QuestionService questionService(){
        return new QuestionImplementation();
    }

    @Bean
    @Scope("singleton")                     //CRIA UM UNICO OBJETO PARA TODO O PROGRAMA 
    public JWTService<Token> jwtService(){
        return new JwtImplementation();
    }

}
