package com.example.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Permission;
import com.example.demo.model.Space;
import com.example.demo.model.User;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PermissionService;

public class PermissionImplementation implements PermissionService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission create(Long spaceId, Long userId, int level) {
        if (userRepository.findById(userId).isEmpty())
            return null;
        
        if (spaceRepository.findById(spaceId).isEmpty())
            return null;

        if(level < 1 || level > 3)
            return null;

        Permission permission = new Permission();
        permission.setSpace(spaceRepository.findById(spaceId).get());
        permission.setUser(userRepository.findById(userId).get());
        permission.setLevel(level);
        permissionRepository.save(permission);

        return permission;
    }

    @Override
    public boolean validatePermission(int level, Long userId, Long spaceId) {
        if(level < 1 || level > 3)
            return false;
        
        if(userRepository.findById(userId).isEmpty())
            return false;
        
        return !spaceRepository.findById(spaceId).isEmpty();
    }

    @Override
    public Boolean updatePermission(Integer level, Long idUser, Long idSpace) {
        Permission permission = permissionRepository.findBySpaceIdSpaceAndUserId(idSpace, idUser);
        Space space = spaceRepository.findByIdSpace(idSpace);
        User user = userRepository.findById(idUser).get();

        if(permission == null){
            permission = new Permission();
            permission.setSpace(space);
            permission.setUser(user);
        }
        
        permission.setLevel(level);
        permissionRepository.save(permission);
        return true;
    }

}
