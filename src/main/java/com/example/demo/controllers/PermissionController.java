package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;
import com.example.demo.dto.permissionDto;
import com.example.demo.model.User;
import com.example.demo.services.PermissionService;
import com.example.demo.services.SpaceService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    UserService userService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    PermissionService permissionService;

    @PostMapping()
    public ResponseEntity<String> postPermission(@RequestBody permissionDto data, @RequestAttribute("token") Token token){
        User user = userService.getById(token.getId());
        if(!spaceService.checkAdm(user.getId(), data.idSpace()))
            return new ResponseEntity<>("Você não possui permissão suficiente", HttpStatus.UNAUTHORIZED);
        permissionService.updatePermission(data.level(), data.idUser(), data.idSpace());
        return new ResponseEntity<>("Nível de permissão atualizado com sucesso! novo nível de permissão: " + data.level(), HttpStatus.OK);
    }   

}
