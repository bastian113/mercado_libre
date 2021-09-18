package com.prueba.mercado.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mutant/")
public class MutantController {

    @GetMapping
    public Boolean isMutant(HttpServletResponse response ) {
        Boolean isMutant = true;

        response.setStatus(HttpServletResponse.SC_OK);
        return isMutant;
    }
}
