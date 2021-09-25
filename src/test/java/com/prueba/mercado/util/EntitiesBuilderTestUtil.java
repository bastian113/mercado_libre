package com.prueba.mercado.util;

import com.prueba.mercado.dto.DNAStatsDTO;

public class EntitiesBuilderTestUtil {

    public static DNAStatsDTO getDNAStatsDTO() {
        return DNAStatsDTO.builder()
                .countMutant(40)
                .countHuman(100)
                .ratio(04)
                .build();
    }
}
