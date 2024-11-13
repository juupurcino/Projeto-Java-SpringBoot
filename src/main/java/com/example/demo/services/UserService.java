package com.example.demo.services;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {
    User create(String name, String email, String edv, String password);  // Aplicar hash na senha e criar usuário e retornar usuário criado
    List<User> get(String query, int page, int size); // get users
}
