package com.jacob.jp.srs.validation;

import com.jacob.jp.srs.exception.EmailAlreadyExistsException;
import com.jacob.jp.srs.exception.EmailNotFoundException;
import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.utils.AlunoMapper;
import org.springframework.stereotype.Component;

@Component
public class AlunoValidator {

    private final AlunoRepository alunoRepository;

    public AlunoValidator(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void validarEmail(AlunoDTO aluno) {
        if (existeEmail(aluno.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
    }
    public void validarMatricula(AlunoDTO aluno) {
        if (existeMatricula(aluno.getMatricula())) {
            throw new IllegalArgumentException("Já existe um aluno com essa matrícula");
        }
    }

    public void validarLogin(String email, String senha) {
        AlunoDTO aluno = buscarPorEmail(email);
        if (!aluno.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }
    }

    public boolean existeEmail(String email) {return this.alunoRepository.existsByEmail(email);}

    public boolean existeMatricula(String matricula) {return this.alunoRepository.existsByMatricula(matricula);}

    public AlunoDTO buscarPorEmail(String email) {
        if (alunoRepository.existsByEmail(email)) {
            return AlunoMapper.toDTO(alunoRepository.findByEmail(email));
        }else {
            throw new EmailNotFoundException();
        }
    }
}