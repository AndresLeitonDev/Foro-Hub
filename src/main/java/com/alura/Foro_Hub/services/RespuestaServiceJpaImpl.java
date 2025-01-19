package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.Foro_Hub.models.Respuesta;
import com.alura.Foro_Hub.repositories.RespuestaRepository;

@Service
public class RespuestaServiceJpaImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Respuesta> findAll() {
        List<Respuesta> cursos = (List<Respuesta>)respuestaRepository.findAll();
        return cursos.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Respuesta> findById(Long id) {
        return respuestaRepository.findById(id);
    }

    @Override
    @Transactional
    public Respuesta save(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    @Override
    @Transactional
    public Optional<Respuesta> update (Respuesta respuesta, Long id) {
        Optional<Respuesta> opt = respuestaRepository.findById(id);
        Respuesta hOpt = null;
        if(opt.isPresent()){
            Respuesta respuestaToUpdate = opt.orElseThrow();
            respuestaToUpdate.setMensaje(respuesta.getMensaje());
            respuestaToUpdate.setTopico(respuesta.getTopico());
            respuestaToUpdate.setFechaCreacion(respuesta.getFechaCreacion());
            respuestaToUpdate.setAutor(respuesta.getAutor());
            respuestaToUpdate.setSolucion(respuesta.getSolucion());
            
            hOpt = respuestaRepository.save(respuestaToUpdate);
        }
        return Optional.ofNullable(hOpt);        
    }

    @Override
    @Transactional
    public void remove(Long id) {
        respuestaRepository.deleteById(id);
    }
}
