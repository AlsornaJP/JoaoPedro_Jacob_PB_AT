package com.jacob.jp.srs.utils;

import com.jacob.jp.srs.DTO.AlunoDTO;
import com.jacob.jp.srs.models.Aluno;
import org.springframework.stereotype.Component;

@Component
public class MapperDeAluno {
    public AlunoDTO toDTO(Aluno aluno){
        return new AlunoDTO(aluno);
    }

    public Aluno toEntity(AlunoDTO alunoDTO){
        return new Aluno(alunoDTO);
    }
}
