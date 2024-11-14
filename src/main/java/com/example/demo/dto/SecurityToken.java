package com.example.demo.dto;

public record SecurityToken (
    String jwt,
    String message
){}
