package com.jacob.jp.srs.Models;

import lombok.Getter;
import lombok.Setter;

public abstract class Usuario {
    private @Getter @Setter Integer id;
    private @Getter @Setter String nome;
    private @Getter @Setter String email;
    private @Getter @Setter String senha;
}
