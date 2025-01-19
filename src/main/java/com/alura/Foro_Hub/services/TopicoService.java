package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;

import com.alura.Foro_Hub.models.Topico;

public interface TopicoService {

    List<Topico> findAll();
    Optional<Topico> findById(Long id);
    List<Topico> findByTitulo(String titulo);
    List<Topico> findByMensaje(String mensaje);
    List<Topico> findByNombreCurso(String nombreCurso);
    List<Topico> findByYear(String year);
    List<Topico> findOrderByFechaCreacionDesc();
    Topico save(Topico curso);
    Optional<Topico> update(Topico curso, Long id);
    void remove(Long id);

}
