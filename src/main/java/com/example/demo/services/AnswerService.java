package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Answer;

public interface AnswerService {

    Answer create(Long questionId, String answer, Long userId); //post resposta
    List<Answer> get(Long questionId, int page, int size);
} 