package com.jacob.jp.srs.ui.menus;

import com.jacob.jp.srs.exception.AvaliacaoAlunoNotFoundException;
import com.jacob.jp.srs.exception.HorarioConflictException;
import com.jacob.jp.srs.exception.TurmaNotFoundException;
import com.jacob.jp.srs.models.DTO.AvaliacaoAlunoDTO;
import com.jacob.jp.srs.models.DTO.DisciplinaDTO;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.models.DTO.SemestreDTO;
import com.jacob.jp.srs.models.DTO.TurmaDTO;
import com.jacob.jp.srs.service.GestaoAtividadesService;
import com.jacob.jp.srs.service.GestaoTurmaService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuProfessor {

    private final GestaoTurmaService gestaoTurmaService;
    private final GestaoAtividadesService gestaoAtividadesService;

    @Setter
    private Scanner scanner;

    public MenuProfessor(GestaoTurmaService gestaoTurmaService,
                         GestaoAtividadesService gestaoAtividadesService) {
        this.gestaoTurmaService      = gestaoTurmaService;
        this.gestaoAtividadesService = gestaoAtividadesService;
    }

    public void startMenu(ProfessorDTO professor) {
        boolean logado = true;
        while (logado) {
            System.out.println("\n=== Menu do Professor — " + professor.getNome() + " ===");
            System.out.println("1. Abrir turma");
            System.out.println("2. Fechar turma");
            System.out.println("3. Lançar nota");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> abrirTurma(professor);
                case "2" -> fecharTurma(professor);
                case "3" -> lancarNota(professor);
                case "0" -> logado = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listarTurmas(ProfessorDTO professor) {
        List<TurmaDTO> turmas = gestaoTurmaService.listarTurmasPorProfessor(professor.getId());
        if (turmas.isEmpty()) {
            System.out.println("Você não possui turmas cadastradas.");
            return;
        }
        System.out.println("\n--- Suas Turmas ---");
        for (TurmaDTO t : turmas) {
            String status = t.isAtivo() ? "Ativa" : "Inativa";
            System.out.printf("ID: %d | %s | %s | %s | %s%n",
                    t.getId(),
                    t.getDisciplina().getNome(),
                    t.getSala(),
                    t.getHorario1(),
                    status);
        }
    }

    private void abrirTurma(ProfessorDTO professor) {
        System.out.println("\n--- Abrir Nova Turma ---");
        try {
            System.out.print("ID da disciplina: ");
            Integer disciplinaId = Integer.parseInt(scanner.nextLine());

            System.out.print("ID do semestre: ");
            Integer semestreId = Integer.parseInt(scanner.nextLine());

            System.out.print("Sala: ");
            String sala = scanner.nextLine();

            System.out.print("Horário 1 (HH:mm): ");
            LocalTime horario1 = LocalTime.parse(scanner.nextLine());

            System.out.print("Horário 2 (HH:mm, opcional — Enter para pular): ");
            String h2input = scanner.nextLine();
            LocalTime horario2 = h2input.isBlank() ? null : LocalTime.parse(h2input);

            TurmaDTO dto = new TurmaDTO();
            ProfessorDTO profRef = new ProfessorDTO();
            profRef.setId(professor.getId());
            DisciplinaDTO discRef = new DisciplinaDTO();
            discRef.setId(disciplinaId);
            SemestreDTO semRef = new SemestreDTO();
            semRef.setId(semestreId);

            dto.setProfessor(profRef);
            dto.setDisciplina(discRef);
            dto.setSemestre(semRef);
            dto.setSala(sala);
            dto.setHorario1(horario1);
            dto.setHorario2(horario2);

            TurmaDTO criada = gestaoTurmaService.abrirTurma(dto);
            System.out.println("Turma aberta com sucesso! ID: " + criada.getId());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (DateTimeParseException e) {
            System.out.println("Formato de horário inválido. Use HH:mm.");
        } catch (HorarioConflictException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void fecharTurma(ProfessorDTO professor) {
        listarTurmas(professor);
        System.out.print("\nID da turma para fechar: ");
        try {
            Integer turmaId = Integer.parseInt(scanner.nextLine());
            gestaoTurmaService.fecharTurma(turmaId);
            System.out.println("Turma fechada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (TurmaNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void lancarNota(ProfessorDTO professor) {
        // placeholder — requer listarAvaliacoesPorTurma, ainda não implementado
        System.out.println("\n[Funcionalidade em desenvolvimento]");
    }
}