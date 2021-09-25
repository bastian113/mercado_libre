package com.prueba.mercado.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verified_dna")
@Entity
public class DNAModel {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "VARCHAR(36) NOT NULL")
    private UUID sequenceId;

    @Column(nullable = false, updatable = false)
    private Boolean isMutant;
}

