package com.example.demo.services;

import com.example.demo.dto.Token;

public interface JWTService<T> {
    String get(T token);
    Token validate(String jwt);
}