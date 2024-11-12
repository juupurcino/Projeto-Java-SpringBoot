package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.services.PassService;

public class PasswordValidatorTest {

    @Autowired
    PassService service;
    
    @Test
    void validateSmallPass() {
        assertEquals(service.verify("oi1"), false);
        assertEquals(service.verify("OI1"), false);
        assertEquals(service.verify("1234"), false);
        assertEquals(service.verify("Ab123"), false);
        assertEquals(service.verify("Aa"), false);
    }

    @Test
    void validateBigPasswords() {
        assertEquals(service.verify("Minhasenha123"), true);
        assertEquals(service.verify("Abc567890123r"), true);
        assertEquals(service.verify("Minhasenhagigantesca1"), true);
        assertEquals(service.verify("sEnhaemoji123"), true);
    }

    @Test
    void validatePasswordsWithNums() {
        assertEquals(service.verify("A123455435436"), false);
        assertEquals(service.verify("123456789012"), false);
        assertEquals(service.verify("oi88oi88oi88"), false);
    }

    @Test
    void validatePassowrdsWithoutNums() {
        assertEquals(service.verify("minhasenhaabC"), false);
        assertEquals(service.verify("mysenhaquaseA"), false);
    }

    @Test
    void validatePassowrdsWithoutLower() {
        assertEquals(service.verify("ABCDSEFDS1234"), false);
        assertEquals(service.verify("1234ADSGDSBDT"), false);
    }

    @Test
    void validatePassowrdsWithoutUpper() {
        assertEquals(service.verify("asfddsfds1234"), false);
        assertEquals(service.verify("1234fdsaergfds"), false);
    }
}
