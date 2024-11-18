package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Permission;
import com.example.demo.model.Space;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.AnswerRepository;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.SpaceService;

import jakarta.transaction.Transactional;

public class SpaceImplementation implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public List<Space> get(String query, int page, int size) {
        List<Space> spaces;

        if(query == null)
            spaces = spaceRepository.findAll();
        else
            spaces = spaceRepository.findByNameContaining(query);
        
        if (spaceRepository.count() < page * size)
            if (spaceRepository.count() > (page - 1) * size)
                return spaces.subList((page - 1) * size, (int)spaceRepository.count());
            else
                return null;
        return spaces.subList((page - 1) * size, page * size);
    }

    @Override
    public Space create(String name, int qtdUsers, Long userId) {
        if (userRepository.findById(userId).isEmpty())
            return null;

        Space space = new Space();
        space.setName(name);
        space.setQtdUsers(qtdUsers);
        spaceRepository.save(space);

        Permission permission = new Permission();
        permission.setSpace(space);
        permission.setLevel(1);
        permission.setUser(userRepository.findById(userId).get());
        permissionRepository.save(permission);
        
        return space;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Space space = spaceRepository.findByIdSpace(id);
        if(space == null)
            return false;

        permissionRepository.deleteAllBySpaceIdSpace(id);
        questionRepository.deleteAllBySpaceIdSpace(id);
        answerRepository.deleteAllBySpaceIdSpace(id);
        spaceRepository.deleteByIdSpace(id);
        return true;
    }

    @Override
    public Space getById(Long id) {
        if (spaceRepository.findById(id).isEmpty())
            return null;
        
        return spaceRepository.findById(id).get();
    }

    @Override
    public Boolean checkAdm(Long userId, Long spaceId) {
        Permission permission = permissionRepository.findBySpaceIdSpaceAndUserId(spaceId, userId);
        if(permission == null)
            return false;
        return permission.getLevel() == 1;
    }
    
}
