package com.jacob.jp.srs.exception;

public class HorarioConflictException extends RuntimeException {
    public HorarioConflictException() { super("Professor já possui uma turma nesse horário."); }
    public HorarioConflictException(String message) { super(message); }
}