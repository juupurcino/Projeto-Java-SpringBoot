package com.example.demo.dto;

import com.example.demo.model.Space;
import com.example.demo.model.User;

public record questionDto(
    String question, String title, Long idSpace
) {
    
}
