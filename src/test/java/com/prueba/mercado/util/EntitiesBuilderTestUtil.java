package com.prueba.mercado.util;

import com.prueba.mercado.domain.DNAModel;
import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.dto.DNAStatsDTO;
import com.prueba.mercado.service.impl.DNAAnalysisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.UUID;

public class EntitiesBuilderTestUtil {

    public static DNAStatsDTO getDNAStatsDTO() {
        return DNAStatsDTO.builder()
                .countMutants(40)
                .countHumans(100)
                .ratio(04)
                .build();
    }

    public static DNASequenceDTO getDNASequenceDTO(String[] sequence) {
        return DNASequenceDTO.builder()
                .sequence(sequence)
                .build();
    }

    public static DNAModel getDNAModel(String[] sequence, boolean isMutant) {
        UUID testUUID = UUID.nameUUIDFromBytes(Arrays.toString(sequence).getBytes());
        return DNAModel.builder()
                .sequenceId(testUUID)
                .isMutant(isMutant)
                .build();
    }
}
