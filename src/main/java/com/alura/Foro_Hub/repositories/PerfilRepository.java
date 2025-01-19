package com.alura.Foro_Hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.Foro_Hub.models.Perfil;
import java.util.List;


public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    List<Perfil> findByNombre(String nombre);
}
