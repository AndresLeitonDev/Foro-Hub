package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.Foro_Hub.models.Usuario;
import com.alura.Foro_Hub.repositories.UsuarioRepository;

@Service
public class UsuarioServiceJpaImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        return usuarios.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails findByNombreUD(String nombre) {
        return usuarioRepository.findByNombreUD(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByNombreOpt(String nombre) {
        return usuarioRepository.findByNombreOpt(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> findByEmailOpt(String email) {
        return usuarioRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(Usuario usuario, Long id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        Usuario hOpt = null;
        if (opt.isPresent()) {
            Usuario usuarioToUpdate = opt.orElseThrow();
            usuarioToUpdate.setNombre(usuario.getNombre());
            usuarioToUpdate.setEmail(usuario.getEmail());
            usuarioToUpdate.setPassword(usuario.getPassword());
            usuarioToUpdate.setPerfiles(usuario.getPerfiles());

            hOpt = usuarioRepository.save(usuarioToUpdate);
        }
        return Optional.ofNullable(hOpt);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        usuarioRepository.deleteById(id);
    }
}
