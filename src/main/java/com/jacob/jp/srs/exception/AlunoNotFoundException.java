package com.jacob.jp.srs.exception;

public class AlunoNotFoundException extends RuntimeException {
    public AlunoNotFoundException() { super("Aluno não encontrado."); }
    public AlunoNotFoundException(String message) { super(message); }
}