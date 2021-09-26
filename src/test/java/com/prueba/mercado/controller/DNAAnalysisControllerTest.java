package com.prueba.mercado.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;
import com.prueba.mercado.service.IDNAAnalysisService;
import com.prueba.mercado.util.EntitiesBuilderTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DNAAnalysisControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IDNAAnalysisService dnaAnalysisService;

    @Test
    public void isMutantTrueTest() throws Exception {
        String[] sequence = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);
        String jsonSequence = objectMapper.writeValueAsString(dnaSequenceDTO);

        when(dnaAnalysisService.isMutant(dnaSequenceDTO)).thenReturn(true);

        mockMvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSequence))
                .andExpect(status().isOk());
    }

    @Test
    public void isMutantFalseTest() throws Exception {
        String[] sequence = {"TTGCGA","CAGTTC","TTATGT","AGAAGG","CCCTTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);
        String jsonSequence = objectMapper.writeValueAsString(dnaSequenceDTO);

        when(dnaAnalysisService.isMutant(dnaSequenceDTO)).thenReturn(false);

        mockMvc.perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSequence))
                .andExpect(status().isForbidden());
    }

    @Test
    public void isMutantInvalidSequenceTest() throws Exception {
        String[] sequence = {"TTGCGA","CAGTTC","TTATGT","AGAAGG","CCCTTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);
        String exceptionMessage = "Invalid nitrogenous base";
        String jsonSequence = objectMapper.writeValueAsString(dnaSequenceDTO);

        when(dnaAnalysisService.isMutant(dnaSequenceDTO)).thenThrow(new DNASequenceFormatException(exceptionMessage));

        mockMvc.perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSequence))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(exceptionMessage)));
    }
}
