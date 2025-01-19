package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.Foro_Hub.models.Topico;
import com.alura.Foro_Hub.repositories.TopicoRepository;

@Service
public class TopicoServiceJpaImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findAll() {
        List<Topico> topicos = (List<Topico>)topicoRepository.findAll();
        return topicos.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Topico> findById(Long id) {
        return topicoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findByTitulo(String titulo) {
        return topicoRepository.findByTitulo(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findByMensaje(String mensaje) {
        return topicoRepository.findByMensaje(mensaje);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findByNombreCurso(String nombreCurso) {
        return topicoRepository.findByNombreCurso(nombreCurso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findByYear(String year) {
        return topicoRepository.findByYear(year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topico> findOrderByFechaCreacionDesc(){
        return topicoRepository.findOrderByFechaCreacionDesc();
    }

    @Override
    @Transactional
    public Topico save(Topico topico) {
        return topicoRepository.save(topico);
    }

    @Override
    @Transactional
    public Optional<Topico> update (Topico topico, Long id) {
        Optional<Topico> opt = topicoRepository.findById(id);
        Topico hOpt = null;
        if(opt.isPresent()){
            Topico topicoToUpdate = opt.orElseThrow();
            topicoToUpdate.setTitulo(topico.getTitulo());
            topicoToUpdate.setMensaje(topico.getMensaje());
            topicoToUpdate.setFechaCreacion(topico.getFechaCreacion());
            topicoToUpdate.setStatus(topico.getStatus());
            topicoToUpdate.setAutor(topico.getAutor());
            topicoToUpdate.setCurso(topico.getCurso());
            //topicoToUpdate.setRespuestas(topico.getRespuestas());
            
            hOpt = topicoRepository.save(topicoToUpdate);
        }
        return Optional.ofNullable(hOpt);        
    }

    @Override
    @Transactional
    public void remove(Long id) {
        topicoRepository.deleteById(id);
    }
}
