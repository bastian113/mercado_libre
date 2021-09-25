package com.prueba.mercado.controller;

import com.prueba.mercado.dto.DNAStatsDTO;
import com.prueba.mercado.service.DNAStatsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class DNAStatsController {

    @Autowired
    DNAStatsService dnaStatsService;

    @ApiOperation("Calculate Statistics")
    @ApiResponse(code = 200, message = "The statistics were calculated successfully")
    @GetMapping("/")
    public ResponseEntity<DNAStatsDTO> getStats() {
        return new ResponseEntity<>(dnaStatsService.getStats(), HttpStatus.OK);
    }
}
