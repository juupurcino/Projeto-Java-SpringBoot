package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;
import com.example.demo.dto.answerDto;
import com.example.demo.model.Answer;
import com.example.demo.model.Permission;
import com.example.demo.model.User;
import com.example.demo.services.AnswerService;
import com.example.demo.services.PermissionService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    
    @Autowired
    AnswerService answerService;
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<String> createAnswer(@RequestBody answerDto newanswer, @RequestAttribute("token") Token token) {
        User user = userService.getById(token.getId());
        Permission permission = permissionService.getByUserId(user.getId(), newanswer.idSpace());
        if(permission == null || permission.getLevel() == 2)
            return new ResponseEntity<>("Permissão insuficiente", HttpStatus.UNAUTHORIZED);

        if(answerService.create(newanswer.idQuestion(), newanswer.answer(), user.getId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Resposta criada com sucesso", HttpStatus.OK);
    }

    @GetMapping("/{question}")
    public ResponseEntity<List<Answer>> getAnswers(@PathVariable Long question, Integer page, Integer size){
        if(page == null)
            page = 1;
        
        if(size == null)
            size = 10;

        if(question == null)
            return new ResponseEntity<>("Missing arguments", HttpStatus.BAD_REQUEST);

        var answers = answerService.get(question, page, size);

        if(answers == null)
            return ResponseEntity.badRequest().build();
        
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}
