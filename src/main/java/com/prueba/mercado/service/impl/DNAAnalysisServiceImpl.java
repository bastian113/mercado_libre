package com.prueba.mercado.service.impl;

import com.prueba.mercado.domain.DNAModel;
import com.prueba.mercado.dto.DNASequenceDTO;
import com.prueba.mercado.exception.DNASequenceFormatException;
import com.prueba.mercado.service.DNAStatsService;
import com.prueba.mercado.service.IDNAAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class DNAAnalysisServiceImpl implements IDNAAnalysisService {

    private static final int VALID_SEQUENCE_SIZE = 4;
    private static final int VALID_OBLIQUE_LEFT_SEQUENCE_SIZE = 3;

    @Autowired
    private DNAStatsService dnaStatsService;

    /**
     * @param sequenceDTO
     * @return true if the sequence belongs to a mutant, false if belongs to a human
     * @throws DNASequenceFormatException
     */
    @Override
    public boolean isMutant(DNASequenceDTO sequenceDTO) throws DNASequenceFormatException {
        String[] sequence = sequenceDTO.getSequence();

        if(sequence == null) {
            throw new DNASequenceFormatException("The DNA sequence can't be null");
        } else {
            UUID sequenceUUID = UUID.nameUUIDFromBytes(getSequenceBytes(sequence));
            Optional<DNAModel> dnaSequence = dnaStatsService.getExistingSequence(sequenceUUID);
            if(dnaSequence.isPresent()) {
                return dnaSequence.get().getIsMutant();
            } else {
                return evaluateDNASequence(sequence, sequenceUUID);
            }
        }
    }


    /**
     * This method process the DNA sequence to determine if it belongs to a mutant or to a human
     * @param sequence
     * @return true if the sequence belongs to a mutant, false if belongs to a human
     * @throws DNASequenceFormatException
     */
    private boolean evaluateDNASequence(String[] sequence, UUID sequenceUUID) throws DNASequenceFormatException {
        int mutantSequencesCount = 0;
        int evalSize = 0;

        for (int i = 0; i < sequence.length; i++) {

            String horizontalSequence = sequence[i];

            for (int j = 0; j < horizontalSequence.length(); j++) {
                    mutantSequencesCount += evaluateHorizontalSequence(horizontalSequence, j, mutantSequencesCount);

                if(sequence.length - i >= 4) {
                    mutantSequencesCount += evaluateVerticalSequence(sequence, j, i, mutantSequencesCount);

                    mutantSequencesCount += evaluateRightObliqueSequence(sequence, horizontalSequence, j, i,
                            mutantSequencesCount);

                    mutantSequencesCount += evaluateLeftObliqueSequence(sequence, horizontalSequence, j, i,
                            mutantSequencesCount);
                }

            }

            if (mutantSequencesCount == 2) {
                saveStats(sequenceUUID, true);
                return true;
            }

        }
        saveStats(sequenceUUID, false);
        return false;
    }

    /**
     * This method builds the horizontal sequence from the current index position to 4 more positions and evaluates if it
     * belongs to a mutant or to a human
     * @param horizontalSequence
     * @param currentPosition
     * @param mutantSequencesCount
     * @return 1 if the sequence belongs to a mutant, 0 if belongs to a human
     * @throws DNASequenceFormatException
     */
    private int evaluateHorizontalSequence(String horizontalSequence, int currentPosition, int mutantSequencesCount)
            throws DNASequenceFormatException {
        int evalSize = currentPosition + VALID_SEQUENCE_SIZE;
        if(mutantSequencesCount < 2 && evalSize <= horizontalSequence.length()) {
            String evalSequence = horizontalSequence.substring(currentPosition, evalSize);
            isValidSequence(evalSequence);
            if(isMutantSequence(evalSequence)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * This method gets the vertical sequence and evaluates if it belongs to a mutant or to a human
     * @param sequence
     * @param currentPosition
     * @param currentSubsequence
     * @param mutantSequencesCount
     * @return 1 if the sequence belongs to a mutant, 0 if belongs to a human
     * @throws DNASequenceFormatException
     */
    private int evaluateVerticalSequence(String[] sequence, int currentPosition, int currentSubsequence,
                                      int mutantSequencesCount) throws DNASequenceFormatException {
        if(mutantSequencesCount < 2) {
            String verticalSequence =  getVerticalSequence(sequence, currentPosition, currentSubsequence);
            isValidSequence(verticalSequence);
            if(isMutantSequence(verticalSequence)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * This method builds the vertical sequence from the current index position to 4 more positions and evaluates if it
     * belongs to a mutant or to a human
     * @param sequence
     * @param currentPosition
     * @param currentSubsequence
     * @return the built vertical sequence
     */
    private String getVerticalSequence (String[] sequence, int currentPosition, int currentSubsequence) {
        StringBuilder verticalSequence = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            verticalSequence.append(sequence[currentSubsequence + i].charAt(currentPosition));
        }

        return verticalSequence.toString();
    }

    /**
     * This method gets the right oblique sequence and evaluates if it belongs to a mutant or to a human
     * @param sequence
     * @param horizontalSequence
     * @param currentPosition
     * @param currentSubsequence
     * @param mutantSequencesCount
     * @return 1 if the sequence belongs to a mutant, 0 if belongs to a human
     * @throws DNASequenceFormatException
     */
    private int evaluateRightObliqueSequence(String[] sequence, String horizontalSequence, int currentPosition,
                                  int currentSubsequence, int mutantSequencesCount) throws DNASequenceFormatException {
        int evalSize = currentPosition + VALID_SEQUENCE_SIZE;
        if(mutantSequencesCount < 2 && evalSize <= horizontalSequence.length()) {
            String rightObliqueSequence = getRightObliqueSequence(sequence, currentPosition, currentSubsequence);
            isValidSequence(rightObliqueSequence);
            if(isMutantSequence(rightObliqueSequence)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * This method builds the right oblique sequence from the current index position to 4 more positions and evaluates
     * if it belongs to a mutant or to a human
     * @param sequence
     * @param currentPosition
     * @param currentSubsequence
     * @return the built right oblique sequence
     */
    private String getRightObliqueSequence(String[] sequence, int currentPosition, int currentSubsequence) {
        StringBuilder rightObliqueSequence = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            rightObliqueSequence.append(sequence[currentSubsequence + i].charAt(currentPosition + i));
        }

        return rightObliqueSequence.toString();
    }

    /**
     * This method gets the left oblique sequence and evaluates if it belongs to a mutant or to a human
     * @param sequence
     * @param horizontalSequence
     * @param currentPosition
     * @param currentSubsequence
     * @param mutantSequencesCount
     * @return 1 if the sequence belongs to a mutant, 0 if belongs to a human
     * @throws DNASequenceFormatException
     */
    private int evaluateLeftObliqueSequence(String[] sequence, String horizontalSequence, int currentPosition,
                                          int currentSubsequence, int mutantSequencesCount) throws DNASequenceFormatException {
        if(mutantSequencesCount < 2 && currentPosition >= VALID_OBLIQUE_LEFT_SEQUENCE_SIZE) {
            String leftObliqueSequence = getLeftObliqueSequence(sequence, currentPosition, currentSubsequence);
            isValidSequence(leftObliqueSequence);
            if(isMutantSequence(leftObliqueSequence)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * This method builds the left oblique sequence from the current index position to 4 fewer positions and evaluates
     * if it belongs to a mutant or to a human
     * @param sequence
     * @param currentPosition
     * @param currentSubsequence
     * @return the built left oblique sequence
     */
    private String getLeftObliqueSequence(String[] sequence, int currentPosition, int currentSubsequence) {
        StringBuilder leftObliqueSequence = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            leftObliqueSequence.append(sequence[currentSubsequence + i].charAt(currentPosition - i));
        }

        return leftObliqueSequence.toString();
    }

    /**
     * This method evaluates if the built sequence has the allowed values (A,T,C,G)
     * @param evalSequence
     * @throws DNASequenceFormatException
     */
    private void isValidSequence(String evalSequence) throws DNASequenceFormatException {
        if(!evalSequence.matches("[ATCG]+")) {
            throw new DNASequenceFormatException("Invalid nitrogenous base " + evalSequence);
        }
    }

    /**
     * This method evaluates if all the characters in the sequence are equals
     * @param evalSequence
     * @return true if all characters are equals, false otherwise
     */
    private boolean isMutantSequence(String evalSequence) {
        return evalSequence.chars().distinct().count() == 1;
    }

    private byte[] getSequenceBytes(String[] sequence) {
        return Arrays.toString(sequence).getBytes();
    }

    private void saveStats(UUID sequenceUUID, boolean isMutant) {
        DNAModel dnaModel = DNAModel.builder()
                                    .sequenceId(sequenceUUID)
                                    .isMutant(isMutant)
                                    .build();
        dnaStatsService.saveSequence(dnaModel);
    }

}
