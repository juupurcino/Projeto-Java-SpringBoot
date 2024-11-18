package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.demo.dto.Token;
import com.example.demo.impl.AnswerImplementation;
import com.example.demo.impl.CriptImplementation;
import com.example.demo.impl.JwtImplementation;
import com.example.demo.impl.LoginImplementation;
import com.example.demo.impl.PassImplementation;
import com.example.demo.impl.PermissionImplementation;
import com.example.demo.impl.QuestionImplementation;
import com.example.demo.impl.SpaceImplementation;
import com.example.demo.impl.UserImplementation;
import com.example.demo.services.AnswerService;
import com.example.demo.services.CriptService;
import com.example.demo.services.JWTService;
import com.example.demo.services.LoginService;
import com.example.demo.services.PassService;
import com.example.demo.services.PermissionService;
import com.example.demo.services.QuestionService;
import com.example.demo.services.SpaceService;
import com.example.demo.services.UserService;


@Configuration
public class DependencyConfiguration {
    // @Bean
    // @Scope("singleton")                  //CRIA UM UNICO OBJETO PARA TODO O PROGRAMA 
    // @Scope("prototype")                  //TODA VEZ QUE PRECISAR DE UM OBJETO, ELE CRIA UM NOVO
    // @Scope("request")                    //PARA UMA ÚNICA REQUISIÇÃO ELE USA O MESMO OBJETO
    // @Scope("session")                    //O OBJETO VAI SAER USADO PARA TUDO DO USUARIO SELECIONAODI
    // public service service(){
    //     return new implementation();
    // }

    @Bean
    @Scope("singleton")
    public CriptService criptService(){
        return new CriptImplementation();
    }
    
    @Bean
    @Scope("singleton")
    public LoginService loginService(){
        return new LoginImplementation();
    }

    @Bean
    @Scope("singleton")
    public UserService userService(){
        return new UserImplementation();
    }

    @Bean
    @Scope("singleton")
    public PassService passService(){
        return new PassImplementation();
    }

    @Bean
    @Scope("singleton")                    
    public JWTService<Token> jwtService(){
        return new JwtImplementation();
    }

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
    @Scope("singleton")
    public AnswerService answerService(){
        return new AnswerImplementation();
    }

    @Bean
    @Scope("singleton")
    public PermissionService permissionService(){
        return new PermissionImplementation();
    }
}
