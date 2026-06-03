package com.jacob.jp.srs.DTO;

import com.jacob.jp.srs.models.Aluno;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
public class AlunoDTO {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;

    public AlunoDTO(Aluno aluno){BeanUtils.copyProperties(aluno, this);}

//    public AlunoDTO(Aluno aluno, String mat){
//        this.id = aluno.getId();
//        this.nome = aluno.getNome();
//        this.email = aluno.getEmail();
//        this.senha = aluno.getSenha();
//        this.matricula = mat;
//    }
}
