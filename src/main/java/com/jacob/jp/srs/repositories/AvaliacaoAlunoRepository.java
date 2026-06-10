package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.AvaliacaoAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoAlunoRepository extends JpaRepository<AvaliacaoAluno, Integer> {
    boolean existsByAlunoIdAndAvaliacaoId(Integer alunoId, Integer avaliacaoId);
    AvaliacaoAluno findByAlunoIdAndAvaliacaoId(Integer alunoId, Integer avaliacaoId);
    List<AvaliacaoAluno> findAllByAlunoId(Integer alunoId);
}