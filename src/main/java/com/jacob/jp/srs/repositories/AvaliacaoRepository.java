package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findAllByTurmaId(Integer turmaId);
}