package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    boolean existsByProfessorIdAndSemestreIdAndHorario1AndAtivoTrue(Integer professorId, Integer semestreId, LocalTime horario1);
    boolean existsByProfessorIdAndSemestreIdAndHorario2AndAtivoTrue(Integer professorId, Integer semestreId, LocalTime horario2);
    List<Turma> findAllByProfessorId(Integer professorId);
}
