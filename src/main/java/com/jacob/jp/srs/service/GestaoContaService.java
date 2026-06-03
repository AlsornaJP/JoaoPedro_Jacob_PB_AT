package com.jacob.jp.srs.service;

import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.validation.AlunoValidator;
import com.jacob.jp.srs.validation.ProfessorValidator;

public class GestaoContaService {

    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;
    private AlunoValidator alunoValidator;
    private ProfessorValidator professorValidator;

    public GestaoContaService(AlunoRepository alunoRepository, ProfessorRepository professorRepository, AlunoValidator alunoValidator, ProfessorValidator professorValidator) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.alunoValidator = alunoValidator;
        this.professorValidator = professorValidator;
    }

    public Aluno registrarAluno(Aluno aluno) {
        alunoValidator.validarEmail(aluno);
//        geradorDeMatricula.gerarMatricula(aluno);
        return alunoRepository.save(aluno);
    }


}
