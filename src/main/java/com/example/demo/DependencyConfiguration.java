package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.impl.SpaceImplementation;
import com.example.demo.services.SpaceService;


@Configuration
public class DependencyConfiguration {
    @Bean
    @Scope("singleton")
    public SpaceService spaceService(){
        return new SpaceImplementation();
    }
}
