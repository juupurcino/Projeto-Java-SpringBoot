package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Question;

public interface QuestionService {
    List<Question> getBySpaceId(Long spaceId, int page, int size);
    Question getById(Long id);
    Question create(String question, String title, Long spaceId, Long userId);
    boolean delete(Long id); 
} 