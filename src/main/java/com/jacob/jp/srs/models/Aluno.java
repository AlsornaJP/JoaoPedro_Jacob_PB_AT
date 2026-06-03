package com.jacob.jp.srs.models;

import com.jacob.jp.srs.DTO.AlunoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name = "alunos")
@NoArgsConstructor
public class Aluno extends Usuario{

    @Column(name = "matricula", length = 9, nullable = false, unique = true)
    private @Getter String matricula;

    public Aluno(AlunoDto alunoDto) {
        BeanUtils.copyProperties(alunoDto, this);
    }
}
