package com.example.demo.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.services.CriptService;

public class CriptImplementation implements CriptService{

    private final BCryptPasswordEncoder encoder;

    public CriptImplementation()
    {
        encoder = new BCryptPasswordEncoder(10);
        System.out.println("BCRYPT CRIADO");
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean compare(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
