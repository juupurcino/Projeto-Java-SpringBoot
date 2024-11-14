package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Question;
import com.example.demo.model.Space;
import com.example.demo.model.User;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.QuestionService;

public class QuestionImplementation implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Question> getBySpaceId(Long spaceId, int page, int size) {
        List<Question> questions = questionRepository.findBySpaceIdSpace(spaceId);       
        if(questions.isEmpty())
            return null;

        Integer start = (page-1) * size; 
        Integer end = page * size;
        if(start >= questions.size() || end >= questions.size() || start < 0 || end < 0)        //Para tratar caso o usuário tente acessar uma pagina inexistente
            return null;

        return questions.subList(start, end);
    }

    @Override
    public Question getById(Long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public Question create(String question, String title, Long spaceId, Long userId) {
        Space space = spaceRepository.findById(userId).get();
        if(space == null)
            return null;

        User user = userRepository.findById(userId).get();
        if(user == null)
            return null;

        Question new_question = new Question();
        new_question.setTitle(title);
        new_question.setQuestion(question);
        new_question.setSpace(space);
        new_question.setUser(user);

        questionRepository.save(new_question);
        return new_question;
    }

    @Override
    public boolean delete(Long id) {
        if(questionRepository.findById(id) == null)
            return false;
        questionRepository.deleteById(id);
        return true;
    }  
}
