package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.DisciplinaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disciplinas")
@Getter
@NoArgsConstructor
public class Disciplina {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    public Disciplina(DisciplinaDTO disciplinaDTO) {
        this.id = disciplinaDTO.getId();
        this.nome = disciplinaDTO.getNome();;
        this.codigo = disciplinaDTO.getCodigo();;
    }
}