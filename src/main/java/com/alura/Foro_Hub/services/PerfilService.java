package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;

import com.alura.Foro_Hub.models.Perfil;

public interface PerfilService {

    List<Perfil> findAll();
    Optional<Perfil> findById(Long id);
    List<Perfil> findByNombre(String nombre);
    Perfil save(Perfil curso);
    Optional<Perfil> update(Perfil curso, Long id);
    void remove(Long id);
}
