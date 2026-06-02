package com.jacob.jp.srs.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AvaliacaoAluno {
    private @Getter @Setter Aluno aluno;
    private @Getter @Setter Avaliacao avaliacao;
    private @Getter @Setter double nota;
    private @Getter @Setter LocalDateTime dataEntrega;
    private @Getter @Setter LocalDateTime dataAlteracao;
    private @Getter @Setter String anotacoes;
}
