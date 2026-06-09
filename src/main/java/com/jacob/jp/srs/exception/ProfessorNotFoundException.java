package com.jacob.jp.srs.exception;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException() { super("Professor não encontrado."); }
    public ProfessorNotFoundException(String message) { super(message); }
}