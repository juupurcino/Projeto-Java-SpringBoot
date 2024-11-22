package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Space;

public record spaceResponse(
    List<Space> space,
    Long total)

{}
