package com.jacob.jp.srs.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private @Getter @Setter String nome;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private @Getter @Setter String email;

    @Column(name = "senha", nullable = false)
    private @Getter @Setter String senha;
}
