package com.jacob.jp.srs.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Avaliacao {
    private @Getter @Setter Integer id;
    private @Getter @Setter LocalDateTime dataEntrega;
    private @Getter @Setter int peso;
    private @Getter @Setter String titulo;
    private @Getter @Setter String enunciado;
}
