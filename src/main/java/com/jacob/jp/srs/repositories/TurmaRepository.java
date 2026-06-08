package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    boolean existsByProfessorIdAndSemestreIdAndHorario1(Integer professorId, Integer semestreId, LocalTime horario1);
    boolean existsByProfessorIdAndSemestreIdAndHorario2(Integer professorId, Integer semestreId, LocalTime horario2);
}
