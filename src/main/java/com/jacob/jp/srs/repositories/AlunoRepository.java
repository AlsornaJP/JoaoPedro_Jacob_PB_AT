package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);

}
