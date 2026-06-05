package com.jacob.jp.srs.validation;

import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.repositories.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoValidator {

    private final AlunoRepository alunoRepository;

    public AlunoValidator(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void validarEmail(AlunoDTO aluno) {
        if (existeEmail(aluno.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno com esse email");
        }
    }
    public void validarMatricula(AlunoDTO aluno) {
        if (existeMatricula(aluno.getMatricula())) {
            throw new IllegalArgumentException("Já existe um aluno com esse email");
        }
    }

    public boolean existeEmail(String email) {return this.alunoRepository.existsByEmail(email);}

    public boolean existeMatricula(String matricula) {return this.alunoRepository.existsByMatricula(matricula);}
}