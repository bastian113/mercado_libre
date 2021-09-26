package com.prueba.mercado.service.impl;

import com.prueba.mercado.domain.DNAModel;
import com.prueba.mercado.dto.DNAStatsDTO;
import com.prueba.mercado.repository.DNAStatsRepository;
import com.prueba.mercado.service.IDNAStatsService;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DNAStatsServiceImpl implements IDNAStatsService {

    @Autowired
    private DNAStatsRepository dnaStatsRepository;

    @Override
    @Cacheable(cacheNames = "existingSequences", key = "#sequenceUUID")
    public Optional<DNAModel> getExistingSequence(UUID sequenceUUID) {
        return dnaStatsRepository.findById(sequenceUUID);
    }

    @Override
    public void saveSequence(DNAModel sequence) {
        dnaStatsRepository.save(sequence);
    }

    /**
     * This method return the statistics
     * @return statistics
     */
    @Override
    public DNAStatsDTO getStats() {
        long mutantsCount = dnaStatsRepository.getMutantsQuantity();
        long humansCount = dnaStatsRepository.getHumansQuantity();

         return DNAStatsDTO.builder()
                .countMutants(mutantsCount)
                .countHumans(humansCount)
                .ratio(calcRatio(mutantsCount, humansCount))
                .build();
    }

    /**
     * @param mutantCount
     * @param humanCount
     * @return calculated ratio with decimal precision of 1
     */
    private Double calcRatio(long mutantCount, long humanCount) {
        if (humanCount != 0) {
           return DoubleRounder.round(((double)mutantCount / humanCount), 1);
        }

        return mutantCount == 0 ? 0d : 1d;
    }
}
