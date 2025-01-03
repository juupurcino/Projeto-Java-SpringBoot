package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;
import com.example.demo.dto.spaceDto;
import com.example.demo.model.Space;
import com.example.demo.model.User;
import com.example.demo.services.SpaceService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;
    
    @PostMapping
    public ResponseEntity<String> createSpace(@RequestBody spaceDto newSpace, @RequestAttribute("token") Token token){
        User user = userService.getById(token.getId());
        if(spaceService.create(newSpace.name(), newSpace.qtdUsers(), user.getId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Espaço criado com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable Long id, @RequestAttribute("token") Token token){
        User user = userService.getById(token.getId());

        if(spaceService.getById(id) == null)
            return new ResponseEntity<>("Este espaço não existe", HttpStatus.UNAUTHORIZED);

        if(!spaceService.checkAdm(user.getId(), id))
            return new ResponseEntity<>("Você não possui permissão para deletar esse espaço", HttpStatus.UNAUTHORIZED);

        if(!spaceService.delete(id))
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Espaço deletado com sucesso", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Space>> getSpaces(String query, Integer page, Integer size){
        if(page == null)
            page = 1;
        
        if(size == null)
            size = 10;
        
        var spaces = spaceService.get(query, page, size);

        if (spaces == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(spaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Space> getQuestionById(@PathVariable Long id){

        if(spaceService.getById(id) == null)
            return ResponseEntity.badRequest().build();
        
        return new ResponseEntity<>(spaceService.getById(id), HttpStatus.OK);
    }
}
