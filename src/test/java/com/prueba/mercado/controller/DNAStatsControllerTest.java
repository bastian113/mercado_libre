package com.prueba.mercado.controller;

import com.prueba.mercado.dto.DNAStatsDTO;
import com.prueba.mercado.service.IDNAStatsService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DNAStatsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IDNAStatsService dnaStatsService;

    @Test
    public void getStatsTest() throws Exception {
        DNAStatsDTO dnaStatsDTO = EntitiesBuilderTestUtil.getDNAStatsDTO();

        when(dnaStatsService.getStats()).thenReturn(dnaStatsDTO);
        mockMvc.perform(get("/stats/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
