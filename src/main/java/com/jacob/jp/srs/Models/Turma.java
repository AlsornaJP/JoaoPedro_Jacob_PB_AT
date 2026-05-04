package com.jacob.jp.srs.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Turma {
    private @Getter @Setter Integer id;

    //    precisa melhorar
    private @Getter @Setter List<TurmaAluno> alunos;

    private @Getter @Setter Semestre semestre;
    private @Getter @Setter Disciplina disciplina;
    private @Getter @Setter Professor professor;
    private @Getter @Setter String sala;
    private @Getter @Setter String horario1;
    private @Getter @Setter String horario2;


}
