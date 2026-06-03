package com.jacob.jp.srs.validation;

import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.repositories.AlunoRepository;
import org.springframework.stereotype.Component;

@Component
public class AlunoValidator {

    private AlunoRepository alunoRepository;

    public AlunoValidator(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void validarEmail(Aluno aluno) {
        if (existeEmail(aluno.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno com esse email");
        }
    }
    public void validarMatricula(Aluno aluno) {
        if (existeMatricula(aluno.getMatricula())) {
            throw new IllegalArgumentException();
        }
    }

    public boolean existeEmail(String email) {return this.alunoRepository.existsByEmail(email);}

    public boolean existeMatricula(String matricula) {return this.alunoRepository.existsByMatricula(matricula);}
}