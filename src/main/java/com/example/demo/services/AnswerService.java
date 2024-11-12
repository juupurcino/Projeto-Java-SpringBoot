package com.example.demo.services;

import com.example.demo.model.Answer;

public interface AnswerService {

    Answer create(Long questionId, String answer, Long userId);
} 