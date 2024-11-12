package com.example.demo.services;

import com.example.demo.model.Permission;

public interface PermissionService {

    Permission create(Long spaceId, long userId, int level);
    
}
