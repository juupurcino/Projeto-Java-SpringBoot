package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Space;

public interface SpaceService {
    List<Space> get(String query, int page, int size);
    Space create(String name, int qtdUsers, Long userId);
    boolean delete(Long id);
} 
