package com.prueba.mercado.service.impl;

import com.prueba.mercado.dto.DNAStatsDTO;
import com.prueba.mercado.repository.DNAStatsRepository;
import com.prueba.mercado.service.IDNAStatsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DNAStatsServiceImplTest {

    @MockBean
    DNAStatsRepository dnaStatsRepository;

    @Autowired
    IDNAStatsService dnaStatsService;

    @Test
    public void getStatsCorrectTest() {
        when(dnaStatsRepository.getMutantsQuantity()).thenReturn(40L);
        when(dnaStatsRepository.getHumansQuantity()).thenReturn(100L);

        DNAStatsDTO result = dnaStatsService.getStats();

        assertThat(result, notNullValue());
        assertThat(result.getCountMutants(), equalTo(40L));
        assertThat(result.getCountHumans(), equalTo(100L));
        assertThat(result.getRatio(), equalTo(0.4D));
    }

    @Test
    public void getStatsErrorZeroHumans() {
        when(dnaStatsRepository.getMutantsQuantity()).thenReturn(40L);
        when(dnaStatsRepository.getHumansQuantity()).thenReturn(0L);

        DNAStatsDTO result = dnaStatsService.getStats();

        assertThat(result, notNullValue());
        assertThat(result.getCountMutants(), equalTo(40L));
        assertThat(result.getCountHumans(), equalTo(0L));
        assertThat(result.getRatio(), equalTo(1D));
    }

}
