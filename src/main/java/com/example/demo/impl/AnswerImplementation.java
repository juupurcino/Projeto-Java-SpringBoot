package com.example.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Answer;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AnswerService;

public class AnswerImplementation implements AnswerService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Answer create(Long questionId, String answer, Long userId) {
        if (userRepository.findById(userId).isEmpty())
            return null;
        
        if (questionRepository.findById(questionId).isEmpty())
            return null;

        Answer newAnswer = new Answer();
        newAnswer.setAnswer(answer);
        newAnswer.setQuestion(questionRepository.findById(questionId).get());
        newAnswer.setSpace(spaceRepository.findById(questionRepository.findById(questionId).get().getSpace().getIdSpace()).get());
        newAnswer.setUser(userRepository.findById(userId).get());
        
        return newAnswer;
    }
    
}
