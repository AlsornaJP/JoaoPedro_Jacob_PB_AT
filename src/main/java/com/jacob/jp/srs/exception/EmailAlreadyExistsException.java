package com.jacob.jp.srs.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {super("Esse endereço de email já está em uso!");}

    public EmailAlreadyExistsException(String message) {super(message);}

}
