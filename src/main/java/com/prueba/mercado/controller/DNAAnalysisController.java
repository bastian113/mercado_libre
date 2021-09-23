package com.prueba.mercado.controller;

import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;
import com.prueba.mercado.service.IDNAAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
@Api(value = "Mutant service")
public class DNAAnalysisController {

    @Autowired
    private IDNAAnalysisService dnaAnalysisService;

    @ApiOperation(value = "Evaluate DNA sequence")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "The DNA sequence belongs to a mutant"),
                    @ApiResponse(code = 403, message = "The DNA sequence is not mutant")
            }
    )
    @PostMapping("/")
    public ResponseEntity<?> isMutant(@RequestBody @Validated DNASequenceDTO sequenceDTO) throws Exception {

        if(dnaAnalysisService.isMutant(sequenceDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

}
