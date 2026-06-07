package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.TurmaAlunoDTO;
import com.jacob.jp.srs.models.DTO.TurmaDTO;
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

    public TurmaAluno(TurmaAlunoDTO turmaAlunoDTO) {
        this.id = turmaAlunoDTO.getId();
        this.aluno = turmaAlunoDTO.getAluno();
        this.turma = turmaAlunoDTO.getTurma();
        this.status = turmaAlunoDTO.getStatus();
    }
}