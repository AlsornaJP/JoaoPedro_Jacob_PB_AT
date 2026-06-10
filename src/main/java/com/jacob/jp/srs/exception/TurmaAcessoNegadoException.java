package com.jacob.jp.srs.exception;

public class TurmaAcessoNegadoException extends RuntimeException {
    public TurmaAcessoNegadoException() { super("Você não tem permissão para acessar essa turma."); }
    public TurmaAcessoNegadoException(String message) { super(message); }
}