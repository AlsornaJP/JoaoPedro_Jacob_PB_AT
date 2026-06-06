package com.jacob.jp.srs.utils;

import com.jacob.jp.srs.models.DTO.ProfessorDTO;
import com.jacob.jp.srs.models.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public static Professor toEntity(ProfessorDTO professorDTO) {return new Professor(professorDTO);}

    public static ProfessorDTO toDTO(Professor professor) {return new ProfessorDTO(professor);}

}
