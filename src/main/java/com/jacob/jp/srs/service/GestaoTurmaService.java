package com.jacob.jp.srs.service;

import com.jacob.jp.srs.exception.DisciplinaNotFoundException;
import com.jacob.jp.srs.exception.HorarioConflictException;
import com.jacob.jp.srs.exception.ProfessorNotFoundException;
import com.jacob.jp.srs.exception.SemestreNotFoundException;
import com.jacob.jp.srs.exception.TurmaNotFoundException;
import com.jacob.jp.srs.models.DTO.DisciplinaDTO;
import com.jacob.jp.srs.models.Disciplina;
import com.jacob.jp.srs.models.DTO.TurmaDTO;
import com.jacob.jp.srs.models.Professor;
import com.jacob.jp.srs.models.Semestre;
import com.jacob.jp.srs.models.Turma;
import com.jacob.jp.srs.repositories.DisciplinaRepository;
import com.jacob.jp.srs.repositories.ProfessorRepository;
import com.jacob.jp.srs.repositories.SemestreRepository;
import com.jacob.jp.srs.repositories.TurmaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GestaoTurmaService {

    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final SemestreRepository semestreRepository;

    public GestaoTurmaService(TurmaRepository turmaRepository,
                              ProfessorRepository professorRepository,
                              DisciplinaRepository disciplinaRepository,
                              SemestreRepository semestreRepository) {
        this.turmaRepository      = turmaRepository;
        this.professorRepository  = professorRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.semestreRepository   = semestreRepository;
    }

    private Semestre buscarSemestreAtivo() {
        LocalDate hoje = LocalDate.now();
        return semestreRepository
                .findByDataInicialLessThanEqualAndDataFinalGreaterThanEqual(hoje, hoje)
                .orElseThrow(() -> new SemestreNotFoundException("Não há semestre ativo para a data atual."));
    }

    public TurmaDTO abrirTurma(TurmaDTO dto) {
        Professor professor = professorRepository.findById(dto.getProfessor().getId())
                .orElseThrow(ProfessorNotFoundException::new);
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplina().getId())
                .orElseThrow(DisciplinaNotFoundException::new);
        Semestre semestre = buscarSemestreAtivo();

        if (turmaRepository.existsByProfessorIdAndSemestreIdAndHorario1AndAtivoTrue(
                professor.getId(), semestre.getId(), dto.getHorario1())) {
            throw new HorarioConflictException();
        }
        if (dto.getHorario2() != null &&
                turmaRepository.existsByProfessorIdAndSemestreIdAndHorario2AndAtivoTrue(
                        professor.getId(), semestre.getId(), dto.getHorario2())) {
            throw new HorarioConflictException();
        }

        Turma turma = turmaRepository.save(new Turma(dto.getId(), professor, disciplina, semestre, dto.getSala(), dto.getHorario1(), dto.getHorario2()));
        return new TurmaDTO(turma);
    }

    public void fecharTurma(Integer id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(TurmaNotFoundException::new);
        turma.fechar();
        turmaRepository.save(turma);
    }

    public List<TurmaDTO> listarTurmasPorProfessor(Integer professorId) {
        return turmaRepository.findAllByProfessorId(professorId)
                .stream()
                .map(TurmaDTO::new)
                .toList();
    }

    public List<DisciplinaDTO> listarDisciplinas() {
        return disciplinaRepository.findAll()
                .stream()
                .map(DisciplinaDTO::new)
                .toList();
    }

    public DisciplinaDTO buscarDisciplinaPorCodigo(String codigo) {
        if (!disciplinaRepository.existsByCodigo(codigo)) {
            throw new DisciplinaNotFoundException();
        }
        return new DisciplinaDTO(disciplinaRepository.findByCodigo(codigo));
    }

    public List<TurmaDTO> listarTurmasDisponiveisPorAluno(Integer alunoId) {
        return turmaRepository.findAllAtivasNaoInscritasPorAluno(alunoId)
                .stream()
                .map(TurmaDTO::new)
                .toList();
    }
}