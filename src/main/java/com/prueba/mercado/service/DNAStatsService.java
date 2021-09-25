package com.prueba.mercado.service;

import com.prueba.mercado.domain.DNAModel;
import com.prueba.mercado.dto.DNAStatsDTO;

import java.util.Optional;
import java.util.UUID;

public interface DNAStatsService {

    Optional<DNAModel> getExistingSequence(UUID sequenceUUID);

    void saveSequence(DNAModel sequence);

    DNAStatsDTO getStats();
}
