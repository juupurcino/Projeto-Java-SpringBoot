package com.example.demo.services;

public interface PermissionService {

    boolean setPermission(Long idUser, Long idSpace, Integer level);
    
}
