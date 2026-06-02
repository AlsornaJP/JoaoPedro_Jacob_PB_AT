package com.jacob.jp.srs.service;

import com.jacob.jp.srs.repositories.AlunoRepository;

public class GestaoContaService {

    private AlunoRepository alunoRepository;

    public GestaoContaService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }


}
