package com.jacob.jp.srs.ui.menus;

import com.jacob.jp.srs.exception.EmailNotFoundException;
import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.service.GestaoContaService;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuLogin {

    GestaoContaService gestaoContaService;

    @Setter
    Scanner scanner;

    public MenuLogin(GestaoContaService gestaoContaService) {
        this.gestaoContaService = gestaoContaService;
    }

    public void startMenu() {
        System.out.println("\n--- Login ---");
        System.out.println("1. Aluno");
        System.out.println("2. Professor");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        switch (scanner.nextLine()) {
            case "1" -> loginAluno();
            case "2" -> loginProfessor();
            case "0" -> {}
            default  -> System.out.println("Opção inválida.");
        }
    }

    private void loginAluno() {
        System.out.println("\n--- Login de Aluno ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            AlunoDTO aluno = gestaoContaService.loginAluno(email, senha);
            System.out.println("Bem-vindo, " + aluno.getNome() + "! (Matrícula: " + aluno.getMatricula() + ")");
        } catch (EmailNotFoundException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void loginProfessor() {
        System.out.println("\n--- Login de Professor ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            ProfessorDTO professor = gestaoContaService.loginProfessor(email, senha);
            System.out.println("Bem-vindo, " + professor.getNome() + "!");
        } catch (EmailNotFoundException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
