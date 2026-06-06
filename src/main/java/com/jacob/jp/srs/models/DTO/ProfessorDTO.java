package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Professor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;

    public ProfessorDTO(Professor professor) {
        this.id    = professor.getId();
        this.nome  = professor.getNome();
        this.email = professor.getEmail();
        this.senha = professor.getSenha();
    }

}
