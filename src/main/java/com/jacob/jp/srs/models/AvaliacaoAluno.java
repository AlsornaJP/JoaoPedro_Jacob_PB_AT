package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.AvaliacaoAlunoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_aluno",
        uniqueConstraints = @UniqueConstraint(name = "uc_avaliacao_aluno", columnNames = {"aluno_id", "avaliacao_id"}) )
@NoArgsConstructor
@Getter
public class AvaliacaoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @Column(name = "nota", nullable = false)
    private double nota;

    @Column(name = "data_entrega", nullable = false)
    private LocalDateTime dataEntrega;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @Column(name = "anotacoes", columnDefinition = "TEXT")
    private String anotacoes;

    public AvaliacaoAluno(AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        this.id =  avaliacaoAlunoDTO.getId();
        this.aluno = avaliacao;
        this.avaliacao = avaliacao;
    }
}
