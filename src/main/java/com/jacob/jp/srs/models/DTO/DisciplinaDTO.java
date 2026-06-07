package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Disciplina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaDTO {

    private Integer id;
    private String nome;
    private String codigo;

    public DisciplinaDTO(Disciplina disciplina) {
        this.id     = disciplina.getId();
        this.nome   = disciplina.getNome();
        this.codigo = disciplina.getCodigo();
    }
}