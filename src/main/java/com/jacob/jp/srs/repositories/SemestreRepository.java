package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
    Optional<Semestre> findByDataInicialLessThanEqualAndDataFinalGreaterThanEqual(LocalDate data, LocalDate data2);
}