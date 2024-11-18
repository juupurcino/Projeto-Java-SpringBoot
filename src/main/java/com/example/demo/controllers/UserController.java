package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SecurityToken;
import com.example.demo.dto.Token;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.services.CriptService;
import com.example.demo.services.JWTService;
import com.example.demo.services.LoginService;
import com.example.demo.services.PassService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping
public class UserController {
    
    @Autowired
    CriptService criptService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    PassService passService;
    @Autowired
    JWTService<Token> jwtService;



    @PostMapping("/user")
    public ResponseEntity<String> createNewUser(@RequestBody UserDto data){
        if(data.edv() == null || data.email() == null || data.name() == null || data.password() == null)
            return new ResponseEntity<>("Missing arguments !", HttpStatus.BAD_REQUEST);
        
        if(!passService.verify(data.password()))
            return new ResponseEntity<>("Invalid Password", HttpStatus.BAD_REQUEST);
        
        User user = userService.create(
            data.name(),
            data.email(),
            data.edv(), 
            data.password()
        );

        if(user == null)
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);


        return new ResponseEntity<>("User created!", HttpStatus.OK);

    }



    @GetMapping("/auth")
    public ResponseEntity<SecurityToken> authentication(@RequestBody UserDto data){
        if((data.edv() == null && data.email() == null && data.name() == null) || data.password() == null)
            return new ResponseEntity<>(new SecurityToken("?","Missing arguments"), HttpStatus.BAD_REQUEST);

        List<User> users = userService.get(
            data.edv() != null ? data.edv() : data.email() != null ? data.email() : data.name(), 
            1,1
        );

        if(users.isEmpty())
            return new ResponseEntity<>(new SecurityToken("?", "None user with these login founded"), HttpStatus.BAD_REQUEST);
            
        User user = users.get(0);
        
        if(!criptService.compare(data.password(), user.getPassword()))
            return new ResponseEntity<>(new SecurityToken("?", "Incorrect password"), HttpStatus.FORBIDDEN);


        Token token = new Token();
        token.setId(user.getId());
        token.setUsername(user.getUsername());
        String jwt = jwtService.get(token);

        return new ResponseEntity<>(new SecurityToken(jwt, "Login authenticated!"), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers(String query, Integer page, Integer size){
        if(page == null)
            page = 1;
        if(size == null)
            size = 10;
        
        List<User> users = userService.get(query, page, size);
        if(users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
