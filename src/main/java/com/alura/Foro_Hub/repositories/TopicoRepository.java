package com.alura.Foro_Hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.Foro_Hub.models.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByTitulo(String titulo);
    List<Topico> findByMensaje(String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso")
    List<Topico> findByNombreCurso(String nombreCurso);

    @Query(value="SELECT t FROM Topico t WHERE EXTRACT(YEAR FROM t.fechaCreacion) = :year")
    List<Topico> findByYear(String year);

    @Query("SELECT t FROM Topico t ORDER BY t.fechaCreacion DESC LIMIT 10")
    List<Topico> findOrderByFechaCreacionDesc();
}
