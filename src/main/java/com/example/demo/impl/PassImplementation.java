package com.example.demo.impl;

import com.example.demo.services.PassService;

public class PassImplementation implements PassService {

    @Override
    public boolean verify(String password) {
        if(!password.matches("[0-9]"))
            return false;
        if(!password.matches("[a-z]"))
            return false;
        if(!password.matches("[A-Z]"))
            return false;
        if(password.length() < 12)
            return false;

        return true;
    }
    
}
