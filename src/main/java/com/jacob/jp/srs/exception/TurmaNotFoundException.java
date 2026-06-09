package com.jacob.jp.srs.exception;

public class TurmaNotFoundException extends RuntimeException {
    public TurmaNotFoundException() { super("Turma não encontrada."); }
    public TurmaNotFoundException(String message) { super(message); }
}