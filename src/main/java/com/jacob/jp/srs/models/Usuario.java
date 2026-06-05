package com.jacob.jp.srs.models;

import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    protected String nome;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    protected String email;

    @Column(name = "senha", nullable = false)
    protected String senha;
}
