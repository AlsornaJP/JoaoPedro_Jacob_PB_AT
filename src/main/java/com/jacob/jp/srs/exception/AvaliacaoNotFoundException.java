package com.jacob.jp.srs.exception;

public class AvaliacaoNotFoundException extends RuntimeException {
    public AvaliacaoNotFoundException() { super("Avaliação não encontrada."); }
    public AvaliacaoNotFoundException(String message) { super(message); }
}