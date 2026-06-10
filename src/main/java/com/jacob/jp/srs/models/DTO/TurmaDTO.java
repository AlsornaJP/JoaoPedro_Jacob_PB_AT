package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Turma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TurmaDTO {

    private Integer id;
    private ProfessorDTO professor;
    private DisciplinaDTO disciplina;
    private SemestreDTO semestre;
    private String sala;
    private LocalTime horario1;
    private LocalTime horario2;
    private boolean ativo;
    private List<TurmaAlunoDTO> alunos;

    public TurmaDTO(Turma turma) {
        this.id         = turma.getId();
        this.professor  = new ProfessorDTO(turma.getProfessor());
        this.disciplina = new DisciplinaDTO(turma.getDisciplina());
        this.semestre   = new SemestreDTO(turma.getSemestre());
        this.sala       = turma.getSala();
        this.horario1   = turma.getHorario1();
        this.horario2   = turma.getHorario2();
        this.ativo = turma.isAtivo();
        this.alunos     = turma.getAlunos().stream()
                .map(TurmaAlunoDTO::new)
                .toList();
    }

    public TurmaDTO(Turma turma, boolean semAlunos) {
        this.id         = turma.getId();
        this.professor  = new ProfessorDTO(turma.getProfessor());
        this.disciplina = new DisciplinaDTO(turma.getDisciplina());
        this.semestre   = new SemestreDTO(turma.getSemestre());
        this.sala       = turma.getSala();
        this.horario1   = turma.getHorario1();
        this.horario2   = turma.getHorario2();
        this.alunos     = null;
    }
}