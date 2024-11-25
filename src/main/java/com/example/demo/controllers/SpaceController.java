package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Token;
import com.example.demo.dto.spaceDto;
import com.example.demo.dto.spaceResponse;
import com.example.demo.model.Space;
import com.example.demo.model.User;
import com.example.demo.repositories.SpaceRepository;
import com.example.demo.services.SpaceService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserService userService;


    // CORS para POST, GET, DELETE e OPTIONS
    @CrossOrigin(
        origins = "http://127.0.0.1:5500", 
        allowedHeaders = "*", 
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS }
    )
    @PostMapping
    public ResponseEntity<String> createSpace(@RequestBody spaceDto newSpace, @RequestAttribute("token") Token token){
        User user = userService.getById(token.getId());
        if(spaceService.create(newSpace.name(), newSpace.qtdUsers(), user.getId()) == null)
            return new ResponseEntity<>("Erro", HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>("Espaço criado com sucesso", HttpStatus.OK);
    }

    @CrossOrigin(
    origins = "http://127.0.0.1:5500", 
    allowedHeaders = "*", 
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS }
)
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
    
    @CrossOrigin(
    origins = "http://127.0.0.1:5500", 
    allowedHeaders = "*", 
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS }
)
    @GetMapping
    public ResponseEntity<Object> getSpaces(String query, Integer page, Integer size){
        if(page == null)
            page = 1;
        
        if(size == null)
            size = 10;
        
        var spaces = spaceService.get(query, page, size);

        var qtdTotal = spaceRepository.findAll().stream().count();

        // Adicionar o dto que eu criei pra retornar o total de spaces, spaceResponse :)

        if (spaces == null) {
            return ResponseEntity.badRequest().build();
        }

        var returnSpace = new spaceResponse(spaces, qtdTotal);

        return new ResponseEntity<>(returnSpace, HttpStatus.OK);
    }

    @CrossOrigin(
        origins = "http://127.0.0.1:5500", 
        allowedHeaders = "*", 
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Space> getQuestionById(@PathVariable Long id){

        if(spaceService.getById(id) == null)
            return ResponseEntity.badRequest().build();
        
        return new ResponseEntity<>(spaceService.getById(id), HttpStatus.OK);
    }
}
