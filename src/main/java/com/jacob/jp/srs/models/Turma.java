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

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TurmaAluno> alunos;

    public Turma(Integer id, Professor professor, Disciplina disciplina, Semestre semestre, String sala, String horario1, String horario2) {
        this.id = id;
        this.professor = professor;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.sala = sala;
        this.horario1 = horario1;
        this.horario2 = horario2;
    }
}

