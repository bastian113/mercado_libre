package com.prueba.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DNASequenceDTO {

    @NotNull(message = "Sequence is mandatory")
    private String[] dna;
}

