package com.example.demo.services;

import com.example.demo.model.Permission;

public interface PermissionService {

    Permission create(Long spaceId, Long userId, int level); //post permissao

    boolean validatePermission (int level, Long userId, Long spaceId);

    Boolean updatePermission(Integer level, Long idUser, Long idSpace);

    Permission getByUserId(Long idUser, Long idSpace);
    
}
