package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Status;
import com.jacob.jp.srs.models.TurmaAluno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TurmaAlunoDTO {

    private Integer id;
    private AlunoDTO aluno;
    private TurmaDTO turma;
    private Status status;

    public TurmaAlunoDTO(TurmaAluno turmaAluno) {
        this.id = turmaAluno.getId();
        this.aluno  = turmaAluno.getAluno();
        this.turma  = turmaAluno.getTurma();
        this.status = turmaAluno.getStatus();
    }
}