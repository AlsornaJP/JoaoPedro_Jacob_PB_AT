package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.AvaliacaoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@NoArgsConstructor
@Getter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "enunciado", nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "data_entrega", nullable = false)
    private LocalDateTime dataEntrega;

    @Column(name = "peso", nullable = false)
    private int peso;

    public Avaliacao(AvaliacaoDTO avaliacaoDTO) {
        this.id = avaliacaoDTO.getId();
        this.turma = avaliacaoDTO.getTurma();
        this.titulo = avaliacaoDTO.getTitulo();
        this.enunciado = avaliacaoDTO.getEnunciado();
        this.dataEntrega = avaliacaoDTO.getDataEntrega();
        this.peso = avaliacaoDTO.getPeso();
    }
}
