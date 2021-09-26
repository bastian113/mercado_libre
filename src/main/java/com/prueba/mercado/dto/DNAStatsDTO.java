package com.prueba.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DNAStatsDTO {

    private long countMutants;

    private long countHumans;

    private double ratio;
}
