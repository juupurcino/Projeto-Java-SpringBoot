package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    
    List<Space> findByNameContaining(String name);
} 
