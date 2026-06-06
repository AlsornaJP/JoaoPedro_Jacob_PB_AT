package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "professor")
public class Professor extends Usuario{

    public  Professor(ProfessorDTO professorDTO) {
        this.id = professorDTO.getId();
        this.nome = professorDTO.getNome();
        this.email = professorDTO.getEmail();
        this.senha = professorDTO.getSenha();
    }
}