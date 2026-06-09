package com.jacob.jp.srs.exception;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException() { super("Nota deve ser um valor entre 0 e 10."); }
    public NotaInvalidaException(String message) { super(message); }
}