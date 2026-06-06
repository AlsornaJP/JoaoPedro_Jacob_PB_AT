package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    boolean existsByEmail(String email);
    Professor findByEmail(String email);
}
