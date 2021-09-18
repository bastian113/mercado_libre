package com.prueba.mercado.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant/")
public class MutantController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean isMutant() {
        Boolean isMutant = true;

        if(true){
            throw new NullPointerException("Null exc");
        }

        return isMutant;
    }
}
