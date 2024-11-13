package com.example.demo.services;

public interface CriptService {
    String encode (String password); // criptografa seha
    boolean compare(String password, String hashedPassword); //comparar senha com senha criptografada
} 