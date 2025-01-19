package com.alura.Foro_Hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.alura.Foro_Hub.models.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNombre(String nombre);

    @Query("select u from Usuario u where u.nombre = ?1")
    UserDetails findByNombreUD(String nombre);

    @Query("select u from Usuario u where u.nombre = ?1")
    Optional<Usuario> findByNombreOpt(String nombre);

    List<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.email = ?1")
    Optional<Usuario> getUserByEmail(String email);
}
