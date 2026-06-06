package com.jacob.jp.srs.ui.menus;

import com.jacob.jp.srs.exception.EmailAlreadyExistsException;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.service.GestaoContaService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuCriarConta {

    GestaoContaService gestaoContaService;

    @Setter
    Scanner scanner;

    public MenuCriarConta(GestaoContaService gestaoContaService) {
        this.gestaoContaService = gestaoContaService;
    }

    public void startMenu() {

        System.out.println("\n--- Criar Conta ---");
        System.out.println("1. Aluno");
        System.out.println("2. Professor");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        switch (scanner.nextLine()) {
            case "1" -> criarContaAluno();
            case "2" -> criarContaProfessor();
            case "0" -> {}
            default  -> System.out.println("Opção inválida.");
        }
    }

    private void criarContaProfessor() {
        System.out.println("\n--- Criar Conta de Professor ---");
        ProfessorDTO dto = new ProfessorDTO();

        System.out.print("Nome: ");
        dto.setNome(scanner.nextLine());
        System.out.print("Email: ");
        dto.setEmail(scanner.nextLine());
        System.out.print("Senha: ");
        dto.setSenha(scanner.nextLine());

        try {
            gestaoContaService.registrarProfessor(dto);
            System.out.println("Conta de professor criada com sucesso!");
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void criarContaAluno() {
        System.out.println("\n--- Criar Conta de Aluno ---");
        AlunoDTO dto = new AlunoDTO();

        System.out.print("Nome: ");
        dto.setNome(scanner.nextLine());
        System.out.print("Email: ");
        dto.setEmail(scanner.nextLine());
        System.out.print("Senha: ");
        dto.setSenha(scanner.nextLine());

        try {
            gestaoContaService.registrarAluno(dto);
            System.out.println("Conta de aluno criada com sucesso!");
        } catch (EmailAlreadyExistsException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}

