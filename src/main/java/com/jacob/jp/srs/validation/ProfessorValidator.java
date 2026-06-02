package com.jacob.jp.srs.validation;


import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import org.springframework.stereotype.Component;

@Component
public class ProfessorValidator {

    private ProfessorRepository professorRepository;

    public ProfessorValidator(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void validarEmail(Professor professor) {
        if (existeEmail(professor.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno com esse email");
        }
    }

    public boolean existeEmail(String email) {return this.professorRepository.existsByEmail(email);}
}
