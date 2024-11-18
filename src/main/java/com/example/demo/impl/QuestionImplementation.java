package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Permission;
import com.example.demo.model.Question;
import com.example.demo.model.Space;
import com.example.demo.model.User;
import com.example.demo.repositories.AnswerRepository;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.QuestionService;

import jakarta.transaction.Transactional;

public class QuestionImplementation implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public List<Question> getBySpaceId(Long spaceId, int page, int size) {
        List<Question> questions = questionRepository.findAllBySpaceIdSpace(spaceId);       
        if(questions.isEmpty())
            return null;

        Integer totalPages = (questions.size() / size) + (questions.size() % size != 0 ? 1 : 0);
        if(page <= 0 || size <= 0 || page > totalPages)
            return List.of();

        if (questionRepository.count() < page * size)
            if (questionRepository.count() > (page - 1) * size)
                return questions.subList((page - 1) * size, (int)questionRepository.count());
            else 
                return null;
            

        return questions.subList((page - 1) * size, page * size);
    }

    @Override
    public Question getById(Long id) {
        if(questionRepository.findById(id).isEmpty())
            return null;
        return questionRepository.findById(id).get();
    }

    @Override
    public Question create(String question, String title, Long spaceId, Long userId) {
        Space space = spaceRepository.findById(spaceId).get();

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

    @Transactional
    @Override
    public boolean delete(Long id) {
        answerRepository.deleteByQuestionId(id);

        if(questionRepository.findById(id).isEmpty())
            return false;
        questionRepository.deleteById(id);
        return true;
    }

    @Override
    public Integer checkPermission(Long userId, Long spaceId) {
        Permission permission = permissionRepository.findBySpaceIdSpaceAndUserId(spaceId, userId);
        if(permission == null)
            return 2;
        return permission.getLevel();
    }

    
}
