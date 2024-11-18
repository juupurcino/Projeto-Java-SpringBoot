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
import com.example.demo.dto.questionDto;
import com.example.demo.model.Permission;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.services.PermissionService;
import com.example.demo.services.QuestionService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    
    @PostMapping
    public ResponseEntity<String> createQuestion(@RequestBody questionDto newQuestion, @RequestAttribute("token") Token token){
        User user = userService.getById(token.getId());
        Permission permission = permissionService.getByUserId(user.getId(), newQuestion.idSpace());

        if(permission == null || permission.getLevel() == 2)
            return new ResponseEntity<>("Você não participa deste espaço!", HttpStatus.UNAUTHORIZED);
        
        if(questionService.create(newQuestion.question(), newQuestion.title(), newQuestion.idSpace(), user.getId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Questão criada com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id){

        if(!questionService.delete(id))
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Questão deletada com sucesso", HttpStatus.OK);
    }

    @GetMapping("/{spaces}")
    public ResponseEntity<List<Question>> getQuestionbySpace(@PathVariable Long spaces, Integer page, Integer size){
        if(page == null)
            page = 1;
        
        if(size == null)
            size = 10;
        
        var space = questionService.getBySpaceId(spaces, page, size);

        if(space == null)
            return ResponseEntity.badRequest().build();
        
        return new ResponseEntity<>(space, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id){
        var question = questionService.getById(id);

        if(question == null)
            return ResponseEntity.badRequest().build();
        
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
}
