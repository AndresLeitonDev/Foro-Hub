package com.alura.Foro_Hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.Foro_Hub.models.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNombre(String nombre);

    List<Usuario> findByEmail(String email);
}
