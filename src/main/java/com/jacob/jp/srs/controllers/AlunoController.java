package com.jacob.jp.srs.controllers;

import com.jacob.jp.srs.models.DTO.AlunoDTO;
import com.jacob.jp.srs.service.GestaoContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final GestaoContaService service;

    public AlunoController(GestaoContaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> CriarConta (@RequestBody AlunoDTO aluno){
        service.registrarAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro concluído com êxito!");
    }
}
