package com.example.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.CriptService;
import com.example.demo.services.UserService;

public class UserImplementation implements UserService{

    @Autowired
    CriptService criptService;
    @Autowired
    UserRepository userRepository;

    @Override
    public User create(String name, String email, String edv, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setEdv(edv);
        user.setPassword(criptService.encode(password));

        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> get(String query, int page, int size) {
        List<User> users;
        
        if(query == null)
            users = userRepository.findAll();
        else
            users = userRepository.searchUser(query);
        Integer totalPages = (users.size() / size) + (users.size() % size != 0 ? 1 : 0);
        if(size > users.size() || page <= 0 || size <= 0 || page > totalPages)
            return List.of();
        
        if (userRepository.count() < page * size)
            if (userRepository.count() > (page - 1) * size)
                return users.subList((page - 1) * size, (int)userRepository.count());
            else
                return null;
        
        return users.subList((page - 1) * size, page * size);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
    
}
