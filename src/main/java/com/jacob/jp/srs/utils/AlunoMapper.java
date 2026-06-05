package com.jacob.jp.srs.utils;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {
    public static AlunoDTO toDTO(Aluno aluno){
        return new AlunoDTO(aluno);
    }

    public static Aluno toEntity(AlunoDTO alunoDTO){
        return new Aluno(alunoDTO);
    }
}
