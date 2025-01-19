package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.Foro_Hub.models.Perfil;
import com.alura.Foro_Hub.repositories.PerfilRepository;

@Service
public class PerfilServiceJpaImpl implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> findAll() {
        List<Perfil> perfiles = (List<Perfil>)perfilRepository.findAll();
        return perfiles.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> findByNombre(String nombre) {
        return perfilRepository.findByNombre(nombre);
    }

    @Override
    @Transactional
    public Perfil save(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    @Transactional
    public Optional<Perfil> update (Perfil perfil, Long id) {
        Optional<Perfil> opt = perfilRepository.findById(id);
        Perfil hOpt = null;
        if(opt.isPresent()){
            Perfil perfilToUpdate = opt.orElseThrow();
            perfilToUpdate.setNombre(perfil.getNombre());
            
            hOpt = perfilRepository.save(perfilToUpdate);
        }
        return Optional.ofNullable(hOpt);        
    }

    @Override
    @Transactional
    public void remove(Long id) {
        perfilRepository.deleteById(id);
    }

}
