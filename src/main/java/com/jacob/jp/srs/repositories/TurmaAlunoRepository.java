package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.TurmaAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Integer> {
    boolean existsByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId);
    Optional<TurmaAluno> findByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId);
    List<TurmaAluno> findAllByAlunoId(Integer alunoId);
}