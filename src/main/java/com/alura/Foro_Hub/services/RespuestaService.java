package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;

import com.alura.Foro_Hub.models.Respuesta;

public interface RespuestaService {

    List<Respuesta> findAll();
    Optional<Respuesta> findById(Long id);
    Respuesta save(Respuesta respuesta);
    Optional<Respuesta> update(Respuesta respuesta, Long id);
    void remove(Long id);
}
