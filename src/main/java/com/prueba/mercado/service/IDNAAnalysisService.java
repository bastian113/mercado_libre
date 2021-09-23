package com.prueba.mercado.service;

import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;

public interface IDNAAnalysisService {

    boolean isMutant(DNASequenceDTO sequence) throws Exception;
}
