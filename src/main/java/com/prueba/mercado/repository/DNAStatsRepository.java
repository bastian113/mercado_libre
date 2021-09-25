package com.prueba.mercado.repository;

import com.prueba.mercado.domain.DNAModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DNAStatsRepository extends JpaRepository<DNAModel, UUID> {

    @Query("SELECT COUNT(d) FROM DNAModel d WHERE d.isMutant = 1")
    long getMutantsQuantity();

    @Query("SELECT COUNT(d) FROM DNAModel d WHERE d.isMutant = 0")
    long getHumansQuantity();
}
