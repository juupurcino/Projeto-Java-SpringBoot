package com.example.demo.services;

public interface CriptService {
    String encode (String password);
    boolean compare(String password, String hashedPassword);
} 