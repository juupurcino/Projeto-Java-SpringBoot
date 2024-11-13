package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.services.CriptService;
import com.example.demo.services.LoginService;
import com.example.demo.services.PassService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    CriptService criptService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    PassService passService;



    @PostMapping
    public ResponseEntity<String> createNewUser(UserDto data){
        if(data.edv() == null || data.email() == null || data.name() == null || data.password() == null)
            return new ResponseEntity<>("Missing arguments for request", HttpStatus.BAD_REQUEST);
        
        if(!passService.verify(data.password()))
            return new ResponseEntity<>("Invalid password!", HttpStatus.BAD_REQUEST);
        
        userService.create(
            data.name(),
            data.email(),
            data.edv(), 
            criptService.encode(data.password())
        );
        return new ResponseEntity<>("User created", HttpStatus.OK);
    }
}
