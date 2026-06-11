package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    Disciplina findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}