package com.jacob.jp.srs.service;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.utils.AlunoMapper;
import com.jacob.jp.srs.utils.GeradorDeMatricula;
import com.jacob.jp.srs.validation.AlunoValidator;
import com.jacob.jp.srs.validation.ProfessorValidator;
import org.springframework.stereotype.Service;

@Service
public class GestaoContaService {

    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;
    private AlunoValidator alunoValidator;
    private ProfessorValidator professorValidator;
    private GeradorDeMatricula geradorDeMatricula;

    public GestaoContaService(AlunoRepository alunoRepository,
                              ProfessorRepository professorRepository,
                              AlunoValidator alunoValidator,
                              ProfessorValidator professorValidator,
                              GeradorDeMatricula geradorDeMatricula) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.alunoValidator = alunoValidator;
        this.professorValidator = professorValidator;
        this.geradorDeMatricula = geradorDeMatricula;
    }

    public String registrarAluno(AlunoDTO aluno) {
        alunoValidator.validarEmail(aluno);
        geradorDeMatricula.gerarMatricula(aluno);
        Aluno novoAluno = alunoRepository.save(AlunoMapper.toEntity(aluno));
        return novoAluno.getMatricula();
    }
}
