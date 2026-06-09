package com.jacob.jp.srs.exception;

public class InscricaoNotFoundException extends RuntimeException {
    public InscricaoNotFoundException() { super("Inscrição não encontrada."); }
    public InscricaoNotFoundException(String message) { super(message); }
}