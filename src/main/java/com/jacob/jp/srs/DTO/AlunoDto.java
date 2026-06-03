package com.jacob.jp.srs.DTO;

import com.jacob.jp.srs.models.Aluno;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
public class AlunoDto {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;

    public AlunoDto(Aluno aluno) {
        BeanUtils.copyProperties(aluno, this);
    }
}
