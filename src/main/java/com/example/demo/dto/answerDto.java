package com.example.demo.dto;

import com.example.demo.model.Question;
import com.example.demo.model.Space;
import com.example.demo.model.User;

public record answerDto(
    String answer, Space space, User user, Question question
) {
    
}
