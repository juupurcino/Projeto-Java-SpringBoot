package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Permission;
import com.example.demo.model.Space;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.SpaceService;

public class SpaceImplementation implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Space> get(String query, int page, int size) {
        if (spaceRepository.count() < page * size) {
            if (spaceRepository.count() > (page - 1) * size)
                return spaceRepository.findByNameContaining(query).subList((page - 1) * size, (int)spaceRepository.count());
            else
                return null;
        }
        return spaceRepository.findByNameContaining(query).subList((page - 1) * size, page * size);
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
        permission.setLevel(3);
        permission.setUser(userRepository.findById(userId).get());
        
        return space;
    }

    @Override
    public boolean delete(Long id) {
        if(spaceRepository.findById(id).isEmpty())
            return false;

        spaceRepository.deleteById(id);
        return true;
    }
    
}
