package com.jacob.jp.srs.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alunos")
public class Aluno extends Usuario{

    @Column(name = "matricula", length = 9, nullable = false, unique = true)
    private @Getter @Setter String matricula;
}
