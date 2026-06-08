package com.jacob.jp.srs.service;

import com.jacob.jp.srs.models.Disciplina;
import com.jacob.jp.srs.models.DTO.TurmaDTO;
import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.models.Semestre;
import com.jacob.jp.srs.models.Turma;
import com.jacob.jp.srs.repositories.DisciplinaRepository;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.repositories.SemestreRepository;
import com.jacob.jp.srs.repositories.TurmaRepository;
import org.springframework.stereotype.Service;

@Service
public class GestaoTurmaService {

    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final SemestreRepository semestreRepository;

    public GestaoTurmaService(TurmaRepository turmaRepository,
                              ProfessorRepository professorRepository,
                              DisciplinaRepository disciplinaRepository,
                              SemestreRepository semestreRepository) {
        this.turmaRepository     = turmaRepository;
        this.professorRepository = professorRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.semestreRepository  = semestreRepository;
    }

    public TurmaDTO abrirTurma(TurmaDTO dto) {
        Professor professor = professorRepository.findById(dto.getProfessor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplina().getId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada."));
        Semestre semestre = semestreRepository.findById(dto.getSemestre().getId())
                .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado."));

        if (turmaRepository.existsByProfessorIdAndSemestreIdAndHorario1(
                professor.getId(), semestre.getId(), dto.getHorario1())) {
            throw new IllegalArgumentException("Professor já possui uma turma nesse horário.");
        }
        if (dto.getHorario2() != null &&
                turmaRepository.existsByProfessorIdAndSemestreIdAndHorario2(
                        professor.getId(), semestre.getId(), dto.getHorario2())) {
            throw new IllegalArgumentException("Professor já possui uma turma nesse horário.");
        }

        Turma turma = turmaRepository.save(new Turma(dto.getId(), professor, disciplina, semestre, dto.getSala(), dto.getHorario1(), dto.getHorario2()));
        return new TurmaDTO(turma);
    }

    public void fecharTurma(Integer id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        turma.fechar();
        turmaRepository.save(turma);
    }
}