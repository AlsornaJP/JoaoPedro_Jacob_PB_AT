package com.jacob.jp.srs.utils;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.validation.AlunoValidator;
import org.springframework.stereotype.Component;


import java.util.concurrent.ThreadLocalRandom;

@Component
public class GeradorDeMatricula {

    private AlunoValidator alunoValidator;

    public GeradorDeMatricula(AlunoValidator alunoValidator) {
        this.alunoValidator = alunoValidator;
    }

    public void gerarMatricula(AlunoDTO aluno){
        if (aluno.getMatricula() == null){
            do {
                aluno.setMatricula(String.format("%09d", ThreadLocalRandom.current().nextInt(1_000_000_000)));
            } while (alunoValidator.existeMatricula(aluno.getMatricula()));
        }
    }
}
