package com.jacob.jp.srs.exception;

public class AvaliacaoAlunoNotFoundException extends RuntimeException {
    public AvaliacaoAlunoNotFoundException() { super("Entrega não encontrada."); }
    public AvaliacaoAlunoNotFoundException(String message) { super(message); }
}