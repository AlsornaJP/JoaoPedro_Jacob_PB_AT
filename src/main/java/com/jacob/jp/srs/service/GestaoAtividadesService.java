package com.jacob.jp.srs.service;

import com.jacob.jp.srs.exception.AlunoNotFoundException;
import com.jacob.jp.srs.exception.AvaliacaoAlunoNotFoundException;
import com.jacob.jp.srs.exception.AvaliacaoNotFoundException;
import com.jacob.jp.srs.exception.InscricaoNotFoundException;
import com.jacob.jp.srs.models.Aluno;
import com.jacob.jp.srs.models.Avaliacao;
import com.jacob.jp.srs.models.AvaliacaoAluno;
import com.jacob.jp.srs.models.DTO.AvaliacaoAlunoDTO;
import com.jacob.jp.srs.repositories.AlunoRepository;
import com.jacob.jp.srs.repositories.AvaliacaoAlunoRepository;
import com.jacob.jp.srs.repositories.AvaliacaoRepository;
import com.jacob.jp.srs.repositories.TurmaAlunoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GestaoAtividadesService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoAlunoRepository avaliacaoAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaAlunoRepository turmaAlunoRepository;

    public GestaoAtividadesService(AvaliacaoRepository avaliacaoRepository,
                                   AvaliacaoAlunoRepository avaliacaoAlunoRepository,
                                   AlunoRepository alunoRepository,
                                   TurmaAlunoRepository turmaAlunoRepository) {
        this.avaliacaoRepository      = avaliacaoRepository;
        this.avaliacaoAlunoRepository = avaliacaoAlunoRepository;
        this.alunoRepository          = alunoRepository;
        this.turmaAlunoRepository     = turmaAlunoRepository;
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
}