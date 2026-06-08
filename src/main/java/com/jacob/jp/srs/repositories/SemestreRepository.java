package com.jacob.jp.srs.repositories;

import com.jacob.jp.srs.models.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
}