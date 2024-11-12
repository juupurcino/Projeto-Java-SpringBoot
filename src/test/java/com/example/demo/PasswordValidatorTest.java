package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.services.CriptService;
import com.example.demo.services.PassService;

public class PasswordValidatorTest {

    @Autowired
    PassService passService;

    @Autowired
    CriptService criptService;
    
    @Test
    void validateSmallPass() {
        assertEquals(passService.verify("oi1"), false);
        assertEquals(passService.verify("OI1"), false);
        assertEquals(passService.verify("1234"), false);
        assertEquals(passService.verify("Ab123"), false);
        assertEquals(passService.verify("Aa"), false);
    }

    @Test
    void validateBigPasswords() {
        assertEquals(passService.verify("Minhasenha123"), true);
        assertEquals(passService.verify("Abc567890123r"), true);
        assertEquals(passService.verify("Minhasenhagigantesca1"), true);
        assertEquals(passService.verify("sEnhaemoji123"), true);
    }

    @Test
    void validatePasswordsWithNums() {
        assertEquals(passService.verify("A123455435436"), false);
        assertEquals(passService.verify("123456789012"), false);
        assertEquals(passService.verify("oi88oi88oi88"), false);
    }

    @Test
    void validatePassowrdsWithoutNums() {
        assertEquals(passService.verify("minhasenhaabC"), false);
        assertEquals(passService.verify("mysenhaquaseA"), false);
    }

    @Test
    void validatePassowrdsWithoutLower() {
        assertEquals(passService.verify("ABCDSEFDS1234"), false);
        assertEquals(passService.verify("1234ADSGDSBDT"), false);
    }

    @Test
    void validatePassowrdsWithoutUpper() {
        assertEquals(passService.verify("asfddsfds1234"), false);
        assertEquals(passService.verify("1234fdsaergfds"), false);
    }

    @Test
    void validateHashedPassoword() {
        assertEquals(criptService.compare("senhaprotegida", criptService.encode("senhaprotegida")), true);
        assertEquals(criptService.compare("meudeus", criptService.encode("meudeus")), true);
        assertEquals(criptService.compare("meudees", criptService.encode("meudeus")), false);
        assertEquals(criptService.compare("meudees", "meudeus"), false);
    }
}
