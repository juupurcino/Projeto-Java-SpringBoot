package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Answer;
import com.example.demo.repositories.AnswerRepository;
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

    @Autowired
    AnswerRepository answerRepository;

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
        answerRepository.save(newAnswer);
        
        return newAnswer;
    }

    @Override
    public List<Answer> get(Long questionId, int page, int size) {
        List<Answer> answers = answerRepository.findbyQuestionIdQuestion(questionId);
        if(answers.isEmpty())
            return null;

        Integer start = (page-1) * size; 
        Integer end = page * size;
        if(start >= answers.size() || end >= answers.size() || start < 0 || end < 0)        //Para tratar caso o usuário tente acessar uma pagina inexistente
            return null;
        
        return answers.subList(start, end);
    }
    
}
