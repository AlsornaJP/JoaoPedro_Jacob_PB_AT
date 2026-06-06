package com.jacob.jp.srs.validation;


import com.jacob.jp.srs.exception.EmailAlreadyExistsException;
import com.jacob.jp.srs.exception.EmailNotFoundException;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.utils.AlunoMapper;
import com.jacob.jp.srs.utils.ProfessorMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfessorValidator {

    private final ProfessorRepository professorRepository;

    public ProfessorValidator(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void validarEmail(ProfessorDTO professor) {
        if (existeEmail(professor.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
    }

    public void validarLogin(String email, String senha) {
        ProfessorDTO professor = buscarPorEmail(email);
        if (!professor.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }
    }

    public boolean existeEmail(String email) {return this.professorRepository.existsByEmail(email);}

    public ProfessorDTO buscarPorEmail(String email) {
        if (professorRepository.existsByEmail(email)) {
            return ProfessorMapper.toDTO(professorRepository.findByEmail(email));
        }else {
            throw new EmailNotFoundException();
        }
    }
}
