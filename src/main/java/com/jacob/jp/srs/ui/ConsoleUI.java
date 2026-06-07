package com.jacob.jp.srs.ui;

import com.jacob.jp.srs.ui.menus.MenuCriarConta;
import com.jacob.jp.srs.ui.menus.MenuLogin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    private final MenuCriarConta menuCriarConta;
    private final MenuLogin menuLogin;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(MenuCriarConta menuCriarConta, MenuLogin menuLogin) {
        this.menuCriarConta = menuCriarConta;
        this.menuLogin = menuLogin;

        menuCriarConta.setScanner(this.scanner);
        menuLogin.setScanner(this.scanner);
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
                case "2" -> menuLogin.startMenu();
                case "0" -> running = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("Até logo!");
    }
}