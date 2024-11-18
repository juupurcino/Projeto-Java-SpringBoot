package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Question;

public interface QuestionService {
    List<Question> getBySpaceId(Long spaceId, int page, int size); // get pergunta do espaço
    Question getById(Long id); // get pergunta pelo id
    Question create(String question, String title, Long spaceId, Long userId); // post pergunta
    boolean delete(Long id); // deletar pergunta
    Integer checkPermission(Long userId, Long spaceId);
} 