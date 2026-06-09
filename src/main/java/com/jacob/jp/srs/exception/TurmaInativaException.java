package com.jacob.jp.srs.exception;

public class TurmaInativaException extends RuntimeException {
    public TurmaInativaException() { super("Turma inativa."); }
    public TurmaInativaException(String message) { super(message); }
}