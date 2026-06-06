package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Aluno;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class AlunoDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;

    public AlunoDTO(Aluno aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();
        this.senha = aluno.getSenha();
        this.matricula = aluno.getMatricula();
    }
}