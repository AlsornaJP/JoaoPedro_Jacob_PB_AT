package com.jacob.jp.srs.service;

import com.jacob.jp.srs.exception.AlunoNotFoundException;
import com.jacob.jp.srs.exception.InscricaoDuplicadaException;
import com.jacob.jp.srs.exception.InscricaoNotFoundException;
import com.jacob.jp.srs.exception.TurmaInativaException;
import com.jacob.jp.srs.exception.TurmaNotFoundException;
import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.DTO.SemestreDTO;
import com.jacob.jp.srs.models.DTO.TurmaAlunoDTO;
import com.jacob.jp.srs.models.Semestre;
import com.jacob.jp.srs.models.Status;
import com.jacob.jp.srs.models.Turma;
import com.jacob.jp.srs.models.TurmaAluno;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.SemestreRepository;
import com.jacob.jp.srs.repositories.TurmaAlunoRepository;
import com.jacob.jp.srs.repositories.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestaoMatriculaService {

    private final SemestreRepository semestreRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaAlunoRepository turmaAlunoRepository;

    public GestaoMatriculaService(SemestreRepository semestreRepository,
                                  TurmaRepository turmaRepository,
                                  AlunoRepository alunoRepository,
                                  TurmaAlunoRepository turmaAlunoRepository) {
        this.semestreRepository   = semestreRepository;
        this.turmaRepository      = turmaRepository;
        this.alunoRepository      = alunoRepository;
        this.turmaAlunoRepository = turmaAlunoRepository;
    }

    public void abrirSemestre(SemestreDTO dto) {
        semestreRepository.save(new Semestre(dto));
    }

    public void solicitarInscricao(TurmaAlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAluno().getId())
                .orElseThrow(AlunoNotFoundException::new);
        Turma turma = turmaRepository.findById(dto.getTurma().getId())
                .orElseThrow(TurmaNotFoundException::new);

        if (!turma.isAtivo()) {
            throw new TurmaInativaException();
        }
        if (turmaAlunoRepository.existsByAlunoIdAndTurmaId(aluno.getId(), turma.getId())) {
            throw new InscricaoDuplicadaException();
        }

        turmaAlunoRepository.save(new TurmaAluno(null, aluno, turma, Status.CURSANDO));
    }

    public void solicitarTrancamento(TurmaAlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAluno().getId())
                .orElseThrow(AlunoNotFoundException::new);
        Turma turma = turmaRepository.findById(dto.getTurma().getId())
                .orElseThrow(TurmaNotFoundException::new);

        TurmaAluno turmaAluno = turmaAlunoRepository.findByAlunoIdAndTurmaId(aluno.getId(), turma.getId())
                .orElseThrow(InscricaoNotFoundException::new);
        turmaAluno.trancar();
        turmaAlunoRepository.save(turmaAluno);
    }

    public List<TurmaAlunoDTO> visualizarGrade(Integer alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(AlunoNotFoundException::new);
        return turmaAlunoRepository.findAllByAlunoId(aluno.getId())
                .stream()
                .map(TurmaAlunoDTO::new)
                .toList();
    }
}