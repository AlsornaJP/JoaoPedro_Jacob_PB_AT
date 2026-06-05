package com.jacob.jp.srs.models;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "alunos")
@NoArgsConstructor
public class Aluno extends Usuario{

    @Column(name = "matricula", length = 9, nullable = false, unique = true)
    private @Getter String matricula;

    public Aluno(AlunoDTO alunoDTO) {
        this.nome = alunoDTO.getNome();
        this.email = alunoDTO.getEmail();
        this.senha = alunoDTO.getSenha();
        this.matricula = alunoDTO.getMatricula();
    }
}