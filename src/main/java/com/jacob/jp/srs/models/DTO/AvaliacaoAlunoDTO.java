package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.AvaliacaoAluno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoAlunoDTO {

    private Integer id;
    private AlunoDTO aluno;
    private AvaliacaoDTO avaliacao;
    private double nota;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataAlteracao;
    private String anotacoes;
    private boolean atraso;
    private boolean corrigido;

    public AvaliacaoAlunoDTO(AvaliacaoAluno avaliacaoAluno) {
        this.id            = avaliacaoAluno.getId();
        this.aluno         = new AlunoDTO(avaliacaoAluno.getAluno());
        this.avaliacao     = new AvaliacaoDTO(avaliacaoAluno.getAvaliacao());
        this.nota          = avaliacaoAluno.getNota();
        this.dataEntrega   = avaliacaoAluno.getDataEntrega();
        this.dataAlteracao = avaliacaoAluno.getDataAlteracao();
        this.anotacoes     = avaliacaoAluno.getAnotacoes();
        this.atraso        = avaliacaoAluno.isAtraso();
        this.corrigido     = avaliacaoAluno.isCorrigido();
    }
}