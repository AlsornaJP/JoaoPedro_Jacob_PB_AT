package com.jacob.jp.srs.ui;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.service.GestaoContaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    private final GestaoContaService gestaoContaService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(GestaoContaService gestaoContaService) {
        this.gestaoContaService = gestaoContaService;
    }

    @Override
    public void run(String... args) {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== SRS - Sistema de Registro ===");
            System.out.println("1. Criar conta de aluno");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1" -> criarContaAluno();
                case "2" -> fazerLogin();
                case "0" -> rodando = false;
                default  -> System.out.println("Opção inválida.");
            }
        }
        System.out.println("Até logo!");
    }

    private void criarContaAluno() {
        System.out.println("\n--- Criar Conta ---");

        AlunoDTO dto = new AlunoDTO();

        System.out.print("Nome: ");
        dto.setNome(scanner.nextLine());

        System.out.print("Email: ");
        dto.setEmail(scanner.nextLine());

        System.out.print("Senha: ");
        dto.setSenha(scanner.nextLine());

        try {
            String matricula = gestaoContaService.registrarAluno(dto);
            System.out.println("Conta criada! Sua matrícula: " + matricula);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
