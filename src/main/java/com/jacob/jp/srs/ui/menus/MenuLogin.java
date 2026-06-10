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

    public Object startMenu() {
        System.out.println("\n--- Login ---");
        System.out.println("1. Aluno");
        System.out.println("2. Professor");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        return switch (scanner.nextLine()) {
            case "1" -> loginAluno();
            case "2" -> loginProfessor();
            case "0" -> null;
            default  -> { System.out.println("Opção inválida."); yield null; }
        };
    }

    private AlunoDTO loginAluno() {
        System.out.println("\n--- Login de Aluno ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            AlunoDTO aluno = gestaoContaService.loginAluno(email, senha);
            System.out.println("Bem-vindo, " + aluno.getNome() + "! (Matrícula: " + aluno.getMatricula() + ")");
            return aluno;
        } catch (EmailNotFoundException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    private ProfessorDTO loginProfessor() {
        System.out.println("\n--- Login de Professor ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            ProfessorDTO professor = gestaoContaService.loginProfessor(email, senha);
            System.out.println("Bem-vindo, " + professor.getNome() + "!");
            return professor;
        } catch (EmailNotFoundException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}
