package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.TurmaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "turmas")
@NoArgsConstructor
@Getter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;

    @Column(name = "sala", nullable = false, length = 20)
    private String sala;

    @Column(name = "horario1", nullable = false, length = 20)
    private String horario1;

    @Column(name = "horario2", length = 20)
    private String horario2;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TurmaAluno> alunos;

    public Turma(TurmaDTO turmaDTO) {
        this.id = turmaDTO.getId();
        this.professor = turmaDTO.getProfessor();
        this.disciplina = turmaDTO.getDisciplina();
        this.semestre = turmaDTO.getSemestre();
        this.sala = turmaDTO.getSala();
        this.horario1 = turmaDTO.getHorario1();
        this.horario2 = turmaDTO.getHorario2();
        this.alunos = turmaDTO.getAlunos();
    }
}

