package com.prueba.mercado.service.impl;

import com.prueba.mercado.domain.DNAModel;
import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;
import com.prueba.mercado.service.IDNAAnalysisService;
import com.prueba.mercado.service.IDNAStatsService;
import com.prueba.mercado.util.EntitiesBuilderTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DNAAnalysisServiceImplTest {

    @Autowired
    IDNAAnalysisService dnaAnalysisService;

    @MockBean
    IDNAStatsService dnaStatsService;

    @Test
    public void isExistingMutant() throws DNASequenceFormatException {
        String[] sequence = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);
        Optional<DNAModel> mutant = Optional.of(EntitiesBuilderTestUtil.getDNAModel(sequence, true));

        when(dnaStatsService.getExistingSequence(any(UUID.class))).thenReturn(mutant);

        assertThat(dnaAnalysisService.isMutant(dnaSequenceDTO), equalTo(true));

    }

    @Test
    public void isNonExistingMutant() throws DNASequenceFormatException {
        String[] sequence = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);

        when(dnaStatsService.getExistingSequence(any(UUID.class))).thenReturn(Optional.empty());
        doNothing().when(dnaStatsService).saveSequence(any(DNAModel.class));

        assertThat(dnaAnalysisService.isMutant(dnaSequenceDTO), equalTo(true));

    }

    @Test
    public void isExistingHuman() throws DNASequenceFormatException {
        String[] sequence = {"TTGCGA","CAGTTC","TTATGT","AGAAGG","CCCTTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);
        Optional<DNAModel> human = Optional.of(EntitiesBuilderTestUtil.getDNAModel(sequence, false));

        when(dnaStatsService.getExistingSequence(any(UUID.class))).thenReturn(human);

        assertThat(dnaAnalysisService.isMutant(dnaSequenceDTO), equalTo(false));

    }

    @Test
    public void isNonExistingHuman() throws DNASequenceFormatException {
        String[] sequence = {"TTGCGA","CAGTTC","TTATGT","AGAAGG","CCCTTA","TCACTG"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);

        when(dnaStatsService.getExistingSequence(any(UUID.class))).thenReturn(Optional.empty());
        doNothing().when(dnaStatsService).saveSequence(any(DNAModel.class));

        assertThat(dnaAnalysisService.isMutant(dnaSequenceDTO), equalTo(false));

    }

    @Test
    public void nullSequenceTest () {
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(null);

        DNASequenceFormatException exception = assertThrows(DNASequenceFormatException.class, () -> dnaAnalysisService.isMutant(dnaSequenceDTO));

        String expectedMessage = "The DNA sequence can't be null";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage, equalTo(actualMessage));
    }

    @Test
    public void nullSubSequenceTest () {
        String[] sequence = {"ATGCGA", null, "TTATGT","AGAAGG","CCCCTA","TCACTA"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);

        DNASequenceFormatException exception = assertThrows(DNASequenceFormatException.class, () -> dnaAnalysisService.isMutant(dnaSequenceDTO));

        String expectedMessage = "Invalid nitrogenous base, null subSequence";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage, equalTo(actualMessage));
    }

    @Test
    public void incompleteSubSequenceTest() {
        String[] sequence = {null, "CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTA"};
        DNASequenceDTO dnaSequenceDTO = EntitiesBuilderTestUtil.getDNASequenceDTO(sequence);

        DNASequenceFormatException exception = assertThrows(DNASequenceFormatException.class, () -> dnaAnalysisService.isMutant(dnaSequenceDTO));

        String expectedMessage = "Invalid nitrogenous base, null subSequence";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage, equalTo(actualMessage));
    }

}
