package com.jacob.jp.srs.ui;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.service.GestaoContaService;
import com.jacob.jp.srs.ui.menus.MenuAluno;
import com.jacob.jp.srs.ui.menus.MenuCriarConta;
import com.jacob.jp.srs.ui.menus.MenuLogin;
import com.jacob.jp.srs.ui.menus.MenuProfessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    private final MenuCriarConta menuCriarConta;
    private final MenuLogin menuLogin;
    private final MenuAluno menuAluno;
    private final MenuProfessor menuProfessor;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(MenuCriarConta menuCriarConta,
                     MenuLogin menuLogin,
                     MenuAluno menuAluno,
                     MenuProfessor menuProfessor) {
        this.menuCriarConta = menuCriarConta;
        this.menuLogin      = menuLogin;
        this.menuAluno      = menuAluno;
        this.menuProfessor  = menuProfessor;

        menuCriarConta.setScanner(this.scanner);
        menuLogin.setScanner(this.scanner);
        menuAluno.setScanner(this.scanner);
        menuProfessor.setScanner(this.scanner);
    }

    @Override
    public void run(String... args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Alumni - Sistema de Registro ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> menuCriarConta.startMenu();
                case "2" -> rotearLogin();
                case "0" -> running = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("Até logo!");
    }

    private void rotearLogin() {
        Object resultado = menuLogin.startMenu();
        if (resultado instanceof AlunoDTO aluno) {
            menuAluno.startMenu(aluno);
        } else if (resultado instanceof ProfessorDTO professor) {
            menuProfessor.startMenu(professor);
        }
    }
}