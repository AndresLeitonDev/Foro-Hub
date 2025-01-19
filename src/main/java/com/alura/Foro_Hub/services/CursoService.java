package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;

import com.alura.Foro_Hub.models.Curso;

public interface CursoService {

    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    Optional<Curso> update(Curso curso, Long id);
    void remove(Long id);
}
