package com.example.demo.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.services.CriptService;

public class CriptImplementation implements CriptService{

    @Override
    public String encode(String password) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Override
    public boolean compare(String password, String hashedPassword) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashedPassword);
    }
    
}
