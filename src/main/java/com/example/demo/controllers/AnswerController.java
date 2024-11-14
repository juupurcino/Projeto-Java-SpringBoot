package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.answerDto;
import com.example.demo.services.AnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    
    @Autowired
    AnswerService answerService;

    @PostMapping
    public ResponseEntity<String> createAnswer(@RequestBody answerDto newanswer) {
        if(answerService.create(newanswer.question().getId(), newanswer.answer(), newanswer.user().getId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Resposta criada com sucesso", HttpStatus.OK);
    }
}
