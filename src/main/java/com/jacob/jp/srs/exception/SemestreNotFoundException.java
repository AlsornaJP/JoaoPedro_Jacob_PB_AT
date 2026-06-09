package com.jacob.jp.srs.exception;

public class SemestreNotFoundException extends RuntimeException {
    public SemestreNotFoundException() { super("Semestre não encontrado."); }
    public SemestreNotFoundException(String message) { super(message); }
}