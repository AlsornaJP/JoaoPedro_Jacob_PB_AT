package com.jacob.jp.srs.models;

import lombok.Getter;
import lombok.Setter;

public class TurmaAluno {
    private @Getter @Setter Aluno aluno;
    private @Getter @Setter Turma turma;
    private @Getter @Setter Status status;
}