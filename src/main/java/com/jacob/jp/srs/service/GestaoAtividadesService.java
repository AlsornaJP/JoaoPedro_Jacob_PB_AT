package com.jacob.jp.srs.service;

import com.jacob.jp.srs.exception.AlunoNotFoundException;
import com.jacob.jp.srs.exception.AvaliacaoAlunoNotFoundException;
import com.jacob.jp.srs.exception.AvaliacaoNotFoundException;
import com.jacob.jp.srs.exception.InscricaoNotFoundException;
import com.jacob.jp.srs.exception.TurmaAcessoNegadoException;
import com.jacob.jp.srs.exception.TurmaInativaException;
import com.jacob.jp.srs.exception.TurmaNotFoundException;
import com.jacob.jp.srs.models.*;
import com.jacob.jp.srs.models.DTO.AvaliacaoAlunoDTO;
import com.jacob.jp.srs.models.DTO.AvaliacaoDTO;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.AvaliacaoAlunoRepository;
import com.jacob.jp.srs.repositories.AvaliacaoRepository;
import com.jacob.jp.srs.repositories.TurmaAlunoRepository;
import com.jacob.jp.srs.repositories.TurmaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GestaoAtividadesService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoAlunoRepository avaliacaoAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaAlunoRepository turmaAlunoRepository;
    private final TurmaRepository turmaRepository;

    public GestaoAtividadesService(AvaliacaoRepository avaliacaoRepository,
                                   AvaliacaoAlunoRepository avaliacaoAlunoRepository,
                                   AlunoRepository alunoRepository,
                                   TurmaAlunoRepository turmaAlunoRepository,
                                   TurmaRepository turmaRepository) {
        this.avaliacaoRepository      = avaliacaoRepository;
        this.avaliacaoAlunoRepository = avaliacaoAlunoRepository;
        this.alunoRepository          = alunoRepository;
        this.turmaAlunoRepository     = turmaAlunoRepository;
        this.turmaRepository          = turmaRepository;
    }

    public AvaliacaoDTO criarAvaliacao(AvaliacaoDTO dto) {
        Turma turma = turmaRepository.findById(dto.getTurma().getId())
                .orElseThrow(TurmaNotFoundException::new);

        if (!turma.isAtivo()) {
            throw new TurmaInativaException();
        }
        if (!turma.getProfessor().getId().equals(dto.getTurma().getProfessor().getId())) {
            throw new TurmaAcessoNegadoException();
        }

        Avaliacao avaliacao = avaliacaoRepository.save(
                new Avaliacao(turma, null, dto.getTitulo(), dto.getEnunciado(), dto.getDataEntrega(), dto.getPeso()));
        return new AvaliacaoDTO(avaliacao);
    }

    public List<AvaliacaoAlunoDTO> listarEntregasPorAvaliacao(Integer avaliacaoId) {
        return avaliacaoAlunoRepository.findAllByAvaliacaoId(avaliacaoId)
                .stream()
                .map(AvaliacaoAlunoDTO::new)
                .toList();
    }

    public void entregarAtividade(AvaliacaoAlunoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAluno().getId())
                .orElseThrow(AlunoNotFoundException::new);
        Avaliacao avaliacao = avaliacaoRepository.findById(dto.getAvaliacao().getId())
                .orElseThrow(AvaliacaoNotFoundException::new);

        if (!turmaAlunoRepository.existsByAlunoIdAndTurmaId(aluno.getId(), avaliacao.getTurma().getId())) {
            throw new InscricaoNotFoundException();
        }

        LocalDateTime agora = LocalDateTime.now();
        boolean atraso = agora.isAfter(avaliacao.getDataEntrega());

        if (avaliacaoAlunoRepository.existsByAlunoIdAndAvaliacaoId(aluno.getId(), avaliacao.getId())) {
            AvaliacaoAluno entregaExistente = avaliacaoAlunoRepository
                    .findByAlunoIdAndAvaliacaoId(aluno.getId(), avaliacao.getId());
            entregaExistente.atualizarEntrega(agora, dto.getAnotacoes(), atraso);
            avaliacaoAlunoRepository.save(entregaExistente);
        } else {
            avaliacaoAlunoRepository.save(new AvaliacaoAluno(null, avaliacao, aluno, agora, 0, null, dto.getAnotacoes(), atraso));
        }
    }

    public void lancarNota(AvaliacaoAlunoDTO dto) {
        AvaliacaoAluno avaliacaoAluno = avaliacaoAlunoRepository.findById(dto.getId())
                .orElseThrow(AvaliacaoAlunoNotFoundException::new);
        avaliacaoAluno.lancarNota(dto.getNota());
        avaliacaoAlunoRepository.save(avaliacaoAluno);
    }

    public List<AvaliacaoAlunoDTO> visualizarDesempenho(Integer alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(AlunoNotFoundException::new);
        return avaliacaoAlunoRepository.findAllByAlunoId(aluno.getId())
                .stream()
                .map(AvaliacaoAlunoDTO::new)
                .toList();
    }

    public List<AvaliacaoDTO> listarAvaliacoesPorTurma(Integer turmaId) {
        return avaliacaoRepository.findAllByTurmaId(turmaId)
                .stream()
                .map(AvaliacaoDTO::new)
                .toList();
    }

    public List<AvaliacaoDTO> listarAvaliacoesDisponiveisPorAluno(Integer alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(AlunoNotFoundException::new);

        List<Integer> turmaIds = turmaAlunoRepository.findAllByAlunoId(aluno.getId())
                .stream()
                .filter(ta -> ta.getStatus() == Status.CURSANDO)
                .map(ta -> ta.getTurma().getId())
                .toList();

        if (turmaIds.isEmpty()) return List.of();

        return avaliacaoRepository.findAllByTurmaIdIn(turmaIds)
                .stream()
                .map(AvaliacaoDTO::new)
                .toList();
    }
}