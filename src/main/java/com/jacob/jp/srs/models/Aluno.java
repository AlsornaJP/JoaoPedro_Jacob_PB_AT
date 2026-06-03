package com.jacob.jp.srs.models;

import com.jacob.jp.srs.DTO.AlunoDTO;
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

    public Aluno(AlunoDTO alunoDto) {
        BeanUtils.copyProperties(alunoDto, this);
    }
}
