package com.example.demo.services;

public interface QuestionService {
    String create(String title, String question);
    String delete(Long id); 
} 