package com.jacob.jp.srs.ui.menus;

import com.jacob.jp.srs.exception.InscricaoDuplicadaException;
import com.jacob.jp.srs.exception.InscricaoNotFoundException;
import com.jacob.jp.srs.exception.TurmaInativaException;
import com.jacob.jp.srs.exception.TurmaNotFoundException;
import com.jacob.jp.srs.exception.AvaliacaoNotFoundException;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.DTO.AvaliacaoAlunoDTO;
import com.jacob.jp.srs.models.DTO.AvaliacaoDTO;
import com.jacob.jp.srs.models.DTO.TurmaAlunoDTO;
import com.jacob.jp.srs.models.DTO.TurmaDTO;
import com.jacob.jp.srs.models.Status;
import com.jacob.jp.srs.service.GestaoAtividadesService;
import com.jacob.jp.srs.service.GestaoMatriculaService;
import com.jacob.jp.srs.service.GestaoTurmaService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuAluno {

    private final GestaoMatriculaService gestaoMatriculaService;
    private final GestaoAtividadesService gestaoAtividadesService;
    private final GestaoTurmaService gestaoTurmaService;

    @Setter
    private Scanner scanner;

    public MenuAluno(GestaoMatriculaService gestaoMatriculaService,
                     GestaoAtividadesService gestaoAtividadesService,
                     GestaoTurmaService gestaoTurmaService) {
        this.gestaoMatriculaService  = gestaoMatriculaService;
        this.gestaoAtividadesService = gestaoAtividadesService;
        this.gestaoTurmaService      = gestaoTurmaService;
    }

    public void startMenu(AlunoDTO aluno) {
        boolean logado = true;
        while (logado) {
            System.out.println("\n=== Menu do Aluno — " + aluno.getNome() + " ===");
            System.out.println("1. Visualizar grade");
            System.out.println("2. Inscrever-se em turma");
            System.out.println("3. Solicitar trancamento de turma");
            System.out.println("4. Entregar atividade");
            System.out.println("5. Visualizar desempenho");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> visualizarGrade(aluno);
                case "2" -> inscreverEmTurma(aluno);
                case "3" -> solicitarTrancamento(aluno);
                case "4" -> entregarAtividade(aluno);
                case "5" -> visualizarDesempenho(aluno);
                case "0" -> logado = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
    }

    private void visualizarGrade(AlunoDTO aluno) {
        List<TurmaAlunoDTO> grade = gestaoMatriculaService.visualizarGrade(aluno.getId());
        if (grade.isEmpty()) {
            System.out.println("Você não está inscrito em nenhuma turma.");
            return;
        }
        System.out.println("\n--- Sua Grade ---");
        for (TurmaAlunoDTO ta : grade) {
            System.out.printf("ID: %d | %s | %s | Horário: %s | Status: %s%n",
                    ta.getTurma().getId(),
                    ta.getTurma().getDisciplina().getNome(),
                    ta.getTurma().getSala(),
                    ta.getTurma().getHorario1(),
                    ta.getStatus());
        }
    }

    private void inscreverEmTurma(AlunoDTO aluno) {
        List<TurmaDTO> disponiveis = gestaoTurmaService.listarTurmasDisponiveisPorAluno(aluno.getId());
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhuma turma disponível para inscrição.");
            return;
        }
        System.out.println("\n--- Turmas Disponíveis ---");
        for (TurmaDTO t : disponiveis) {
            System.out.printf("ID: %d | %s | Sala: %s | Horário: %s | Professor: %s%n",
                    t.getId(),
                    t.getDisciplina().getNome(),
                    t.getSala(),
                    t.getHorario1(),
                    t.getProfessor().getNome());
        }

        System.out.print("\nID da turma: ");
        try {
            Integer turmaId = Integer.parseInt(scanner.nextLine());

            TurmaAlunoDTO dto = new TurmaAlunoDTO();
            AlunoDTO alunoRef = new AlunoDTO();
            alunoRef.setId(aluno.getId());
            TurmaDTO turmaRef = new TurmaDTO();
            turmaRef.setId(turmaId);
            dto.setAluno(alunoRef);
            dto.setTurma(turmaRef);

            gestaoMatriculaService.solicitarInscricao(dto);
            System.out.println("Inscrição realizada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (TurmaNotFoundException | TurmaInativaException | InscricaoDuplicadaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void solicitarTrancamento(AlunoDTO aluno) {
        List<TurmaAlunoDTO> cursando = gestaoMatriculaService.visualizarGrade(aluno.getId())
                .stream()
                .filter(ta -> ta.getStatus() == Status.CURSANDO)
                .toList();

        if (cursando.isEmpty()) {
            System.out.println("Você não possui turmas em andamento.");
            return;
        }
        System.out.println("\n--- Turmas em Andamento ---");
        for (TurmaAlunoDTO ta : cursando) {
            System.out.printf("ID: %d | %s | Sala: %s | Horário: %s%n",
                    ta.getTurma().getId(),
                    ta.getTurma().getDisciplina().getNome(),
                    ta.getTurma().getSala(),
                    ta.getTurma().getHorario1());
        }

        System.out.print("\nID da turma para trancamento: ");
        try {
            Integer turmaId = Integer.parseInt(scanner.nextLine());

            TurmaAlunoDTO dto = new TurmaAlunoDTO();
            AlunoDTO alunoRef = new AlunoDTO();
            alunoRef.setId(aluno.getId());
            TurmaDTO turmaRef = new TurmaDTO();
            turmaRef.setId(turmaId);
            dto.setAluno(alunoRef);
            dto.setTurma(turmaRef);

            gestaoMatriculaService.solicitarTrancamento(dto);
            System.out.println("Trancamento realizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (TurmaNotFoundException | InscricaoNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void entregarAtividade(AlunoDTO aluno) {
        List<AvaliacaoDTO> avaliacoes = gestaoAtividadesService.listarAvaliacoesDisponiveisPorAluno(aluno.getId());
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação disponível.");
            return;
        }
        System.out.println("\n--- Avaliações Disponíveis ---");
        for (AvaliacaoDTO a : avaliacoes) {
            System.out.printf("ID: %d | %s | Turma: %s | Prazo: %s%n",
                    a.getId(),
                    a.getTitulo(),
                    a.getTurma().getDisciplina().getNome(),
                    a.getDataEntrega());
        }

        System.out.print("\nID da avaliação: ");
        try {
            Integer avaliacaoId = Integer.parseInt(scanner.nextLine());
            System.out.print("Anotações (opcional, Enter para pular): ");
            String anotacoes = scanner.nextLine();

            AvaliacaoAlunoDTO dto = new AvaliacaoAlunoDTO();
            AlunoDTO alunoRef = new AlunoDTO();
            alunoRef.setId(aluno.getId());
            AvaliacaoDTO avaliacaoRef = new AvaliacaoDTO();
            avaliacaoRef.setId(avaliacaoId);
            dto.setAluno(alunoRef);
            dto.setAvaliacao(avaliacaoRef);
            dto.setAnotacoes(anotacoes.isBlank() ? null : anotacoes);

            gestaoAtividadesService.entregarAtividade(dto);
            System.out.println("Atividade entregue com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (AvaliacaoNotFoundException | InscricaoNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void visualizarDesempenho(AlunoDTO aluno) {
        List<AvaliacaoAlunoDTO> desempenho = gestaoAtividadesService.visualizarDesempenho(aluno.getId());
        if (desempenho.isEmpty()) {
            System.out.println("Nenhuma entrega registrada.");
            return;
        }
        System.out.println("\n--- Seu Desempenho ---");
        for (AvaliacaoAlunoDTO aa : desempenho) {
            String nota = aa.isCorrigido() ? String.valueOf(aa.getNota()) : "Aguardando correção";
            System.out.printf("Avaliação: %s | Nota: %s | Atraso: %s%n",
                    aa.getAvaliacao().getTitulo(),
                    nota,
                    aa.isAtraso() ? "Sim" : "Não");
        }
    }
}