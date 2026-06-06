package com.jacob.jp.srs.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {super("Endereço de email não encontrado.");}

    public EmailNotFoundException(String message) {
        super(message);
    }
}
