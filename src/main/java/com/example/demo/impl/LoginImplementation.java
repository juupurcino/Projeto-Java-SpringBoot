package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.Token;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.JWTService;
import com.example.demo.services.LoginService;

public class LoginImplementation implements LoginService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTService<Token> jwtService;

    @Override
    public String login(String edv, String password) {

        List<User> users = userRepository.searchUser(edv);
        if(users.isEmpty())
            return null;
        
        User user = users.get(0);

        Token token = new Token();
        token.setId(user.getId());
        token.setUsername(user.getUsername());

        return jwtService.get(token);
    }
}
