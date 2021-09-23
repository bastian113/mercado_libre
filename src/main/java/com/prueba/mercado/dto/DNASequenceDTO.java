package com.prueba.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DNASequenceDTO {

    @NotNull(message = "Sequence is mandatory")
    private String[] sequence;
}

