package com.jacob.jp.srs.models;

import com.jacob.jp.srs.exception.NotaInvalidaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_aluno",
        uniqueConstraints = @UniqueConstraint(name = "uc_avaliacao_aluno", columnNames = {"aluno_id", "avaliacao_id"}))
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

    @Column(name = "nota")
    private double nota;

    @Column(name = "data_entrega", nullable = false)
    private LocalDateTime dataEntrega;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @Column(name = "anotacoes", columnDefinition = "TEXT")
    private String anotacoes;

    @Column(name = "atraso", nullable = false)
    private boolean atraso;

    @Column(name = "corrigido", nullable = false)
    private boolean corrigido = false;

    public AvaliacaoAluno(Integer id, Avaliacao avaliacao, Aluno aluno, LocalDateTime dataEntrega, double nota, LocalDateTime dataAlteracao, String anotacoes, boolean atraso) {
        this.id            = id;
        this.avaliacao     = avaliacao;
        this.aluno         = aluno;
        this.dataEntrega   = dataEntrega;
        this.nota          = nota;
        this.dataAlteracao = dataAlteracao;
        this.anotacoes     = anotacoes;
        this.atraso        = atraso;
        this.corrigido     = false;
    }

    public void lancarNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException();
        }
        this.nota      = nota;
        this.corrigido = true;
    }

    public void atualizarEntrega(LocalDateTime dataAlteracao, String anotacoes, boolean atraso) {
        this.dataAlteracao = dataAlteracao;
        this.anotacoes     = anotacoes;
        this.atraso        = atraso;
    }
}