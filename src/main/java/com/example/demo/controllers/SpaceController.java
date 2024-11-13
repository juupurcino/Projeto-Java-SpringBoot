package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.spaceDto;
import com.example.demo.model.Space;
import com.example.demo.services.SpaceService;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    SpaceService spaceService;
    
    @PostMapping
    public ResponseEntity<String> createSpace(@RequestBody spaceDto newSpace){
        if(spaceService.create(newSpace.name(), newSpace.qtdUsers(), newSpace.userId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Espaço criado com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpace(@PathVariable String id){
        if(spaceService.delete(Long.valueOf(id)))
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Espaço deletado com sucesso", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Space>> getSpaces(String query, int page, int size){
        // if(page == null)
        //     page = 1;
        
        // if(size)
        //     size = 10;

        System.out.println(page);
        System.out.println(size);
        
        return new ResponseEntity<>(spaceService.get(query, page, size), HttpStatus.OK);
    }
}
