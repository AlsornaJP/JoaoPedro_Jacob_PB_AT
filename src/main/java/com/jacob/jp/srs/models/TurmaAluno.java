package com.jacob.jp.srs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "turma_aluno",
        uniqueConstraints = @UniqueConstraint(name = "uc_turma_aluno", columnNames = {"aluno_id", "turma_id"}))
@NoArgsConstructor
@Getter
public class TurmaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public TurmaAluno(Integer id, Aluno aluno, Turma turma, Status status) {
        this.id = id;
        this.aluno = aluno;
        this.turma = turma;
        this.status = status;
    }

    public void trancar() {
        this.status = Status.TRANCADO;
    }
}