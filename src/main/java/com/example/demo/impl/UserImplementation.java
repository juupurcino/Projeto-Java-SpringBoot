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
            
        if(users.isEmpty())
            return List.of();
        if(size > users.size())
            return List.of();
        
        Integer start = (page-1) * size; 
        Integer end = page * size;
        // Integer totalPages = (users.size() / size) + (users.size() % size != 0 ? 1 : 0);        //Util talvez?
        
        if(start > users.size() || end > users.size() || start < 0 || end < 0)
            return null;

        return users.subList(start, end);
    }
    
}
