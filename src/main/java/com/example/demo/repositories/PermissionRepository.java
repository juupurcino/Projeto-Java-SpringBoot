package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findBySpaceIdSpaceAndUserId(Long idSpace, Long idUser);
    void deleteAllBySpaceIdSpace(Long id);
    Permission findByUserIdAndSpaceIdSpace(Long idUser, Long idSpace);
}
