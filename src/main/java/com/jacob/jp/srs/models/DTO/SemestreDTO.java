package com.jacob.jp.srs.models.DTO;

import com.jacob.jp.srs.models.Semestre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class SemestreDTO {

    private Integer id;
    private LocalDate dataInicial;
    private LocalDate dataFinal;

    public SemestreDTO(Semestre semestre) {
        this.id          = semestre.getId();
        this.dataInicial = semestre.getDataInicial();
        this.dataFinal   = semestre.getDataFinal();
    }
}
