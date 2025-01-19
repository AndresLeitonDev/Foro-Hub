package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;

import com.alura.Foro_Hub.models.Usuario;

public interface UsuarioService {

    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    List<Usuario> findByNombre(String nombre);
    List<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
    Optional<Usuario> update(Usuario usuario, Long id);
    void remove(Long id);

}
