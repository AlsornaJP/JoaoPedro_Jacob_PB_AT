package com.jacob.jp.srs.exception;

public class InscricaoDuplicadaException extends RuntimeException {
    public InscricaoDuplicadaException() { super("Aluno já inscrito nessa turma."); }
    public InscricaoDuplicadaException(String message) { super(message); }
}