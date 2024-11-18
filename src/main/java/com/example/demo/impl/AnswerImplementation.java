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
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        if(answers.isEmpty())
            return null;

        Integer totalPages = (answers.size() / size) + (answers.size() % size != 0 ? 1 : 0);
        if(page <= 0 || size <= 0 || page > totalPages)
            return List.of();

        if (answerRepository.count() < page * size)
            if (answerRepository.count() > (page - 1) * size) {
                return answers.subList((page - 1) * size, (int)answerRepository.count());
            } else {
                return null;
            }
        
        return answers.subList((page - 1) * size, page * size);
    }
    
}
