package com.example.demo.dto;

public record permissionDto(
    Long idSpace,
    Long idUser,    //usuario que vai receber a permissao
    Integer level 
){}
