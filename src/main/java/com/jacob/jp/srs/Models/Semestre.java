package com.jacob.jp.srs.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Semestre {
    private @Getter @Setter Integer id;
    private @Getter @Setter LocalDate dataInicial;
    private @Getter @Setter LocalDate dataFinal;
}
