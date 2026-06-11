package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    boolean existsByProfessorIdAndSemestreIdAndHorario1AndAtivoTrue(Integer professorId, Integer semestreId, LocalTime horario1);
    boolean existsByProfessorIdAndSemestreIdAndHorario2AndAtivoTrue(Integer professorId, Integer semestreId, LocalTime horario2);
    List<Turma> findAllByProfessorId(Integer professorId);

    @Query("SELECT t FROM Turma t WHERE t.ativo = true AND t.id NOT IN " +
            "(SELECT ta.turma.id FROM TurmaAluno ta WHERE ta.aluno.id = :alunoId)")
    List<Turma> findAllAtivasNaoInscritasPorAluno(@Param("alunoId") Integer alunoId);
}
