package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Avaliacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO {

    private Integer id;
    private TurmaDTO turma;
    private String titulo;
    private String enunciado;
    private LocalDateTime dataEntrega;
    private int peso;

    public AvaliacaoDTO(Avaliacao avaliacao) {
        this.id          = avaliacao.getId();
        this.turma       = new TurmaDTO(avaliacao.getTurma());
        this.titulo      = avaliacao.getTitulo();
        this.enunciado   = avaliacao.getEnunciado();
        this.dataEntrega = avaliacao.getDataEntrega();
        this.peso        = avaliacao.getPeso();
    }
}