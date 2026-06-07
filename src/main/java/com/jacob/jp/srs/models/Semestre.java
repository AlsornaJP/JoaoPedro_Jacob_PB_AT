package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.SemestreDTO;
import lombok.Getter;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "semestres")
@NoArgsConstructor
@Getter
public class Semestre {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inicial", nullable = false)
    private LocalDate dataInicial;

    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    public Semestre(SemestreDTO semestreDTO) {
        this.id = semestreDTO.getId();
        this.dataInicial = semestreDTO.getDataInicial();
        this.dataFinal = semestreDTO.getDataFinal();
    }
}
