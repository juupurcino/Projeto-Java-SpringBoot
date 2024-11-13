package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
}
