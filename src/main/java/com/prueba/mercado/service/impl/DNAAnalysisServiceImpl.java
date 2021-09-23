package com.prueba.mercado.service.impl;

import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;
import com.prueba.mercado.service.IDNAAnalysisService;
import org.springframework.stereotype.Service;

@Service
public class DNAAnalysisServiceImpl implements IDNAAnalysisService {

    private static final int VALID_SEQUENCE_SIZE = 4;

    @Override
    public boolean isMutant(DNASequenceDTO sequenceDTO) throws DNASequenceFormatException {
        String[] sequence = sequenceDTO.getSequence();

        if(sequence == null) {
            throw new DNASequenceFormatException("The DNA sequence can't be null");
        } else {
            checkDNASequence(sequence);
        }

        return true;
    }

    private boolean checkDNASequence(String[] sequence) throws DNASequenceFormatException {

        int mutantSequences = 0;
        int evalSize = 0;

        for (int i = 0; i < sequence.length; i++) {

            String horizontalSequence = sequence[i];

            for (int j = 0; j < horizontalSequence.length(); j++) {
                evalSize = j + VALID_SEQUENCE_SIZE;
                if (evalSize <= horizontalSequence.length()) {
                    mutantSequences += checkHorizontalSequence(horizontalSequence, j, evalSize);
                } else {
                    break;
                }
            }

        }

        return false;
    }

    private int checkHorizontalSequence(String horizontalSequence, int currentPosition, int evalSize) throws DNASequenceFormatException {
            String evalSequence = horizontalSequence.substring(currentPosition, evalSize);
            isValidSequence(evalSequence);
            isMutantSequence(evalSequence);

        return 1;
    }

    private void isValidSequence(String evalSequence) throws DNASequenceFormatException {
        if(!evalSequence.matches("[ATCG]+")) {
            throw new DNASequenceFormatException("Invalid nitrogenous base " + evalSequence);
        }
    }

    private boolean isMutantSequence(String evalSequence) {
        evalSequence.chars().distinct().count();
        return true;
    }
}
