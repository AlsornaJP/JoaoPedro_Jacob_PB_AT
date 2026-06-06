package com.jacob.jp.srs.service;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.utils.AlunoMapper;
import com.jacob.jp.srs.utils.GeradorDeMatricula;
import com.jacob.jp.srs.utils.ProfessorMapper;
import com.jacob.jp.srs.validation.AlunoValidator;
import com.jacob.jp.srs.validation.ProfessorValidator;
import org.springframework.stereotype.Service;

@Service
public class GestaoContaService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoValidator alunoValidator;
    private final ProfessorValidator professorValidator;
    private final GeradorDeMatricula geradorDeMatricula;

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

    public void registrarAluno(AlunoDTO aluno) {
        alunoValidator.validarEmail(aluno);
        geradorDeMatricula.gerarMatricula(aluno);
        Aluno novoAluno = alunoRepository.save(AlunoMapper.toEntity(aluno));
    }

    public void registrarProfessor(ProfessorDTO professor) {
        professorValidator.validarEmail(professor);
        Professor novoProfessor = professorRepository.save(ProfessorMapper.toEntity(professor));
    }

    public AlunoDTO loginAluno(String email, String senha) {
        alunoValidator.validarLogin(email, senha);
        return alunoValidator.buscarPorEmail(email);
    }

    public ProfessorDTO loginProfessor(String email, String senha) {
        professorValidator.validarLogin(email, senha);
        return professorValidator.buscarPorEmail(email);
    }

}
