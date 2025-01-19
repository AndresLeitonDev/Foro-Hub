package com.alura.Foro_Hub.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.Foro_Hub.models.Curso;
import com.alura.Foro_Hub.repositories.CursoRepository;

@Service
public class CursoServiceJpaImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        List<Curso> cursos = (List<Curso>)cursoRepository.findAll();
        return cursos.stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public Optional<Curso> update (Curso curso, Long id) {
        Optional<Curso> opt = cursoRepository.findById(id);
        Curso hOpt = null;
        if(opt.isPresent()){
            Curso cursoToUpdate = opt.orElseThrow();
            cursoToUpdate.setNombre(curso.getNombre());
            cursoToUpdate.setCategoria(curso.getCategoria());
            hOpt = cursoRepository.save(cursoToUpdate);
        }
        return Optional.ofNullable(hOpt);        
    }

    @Override
    @Transactional
    public void remove(Long id) {
        cursoRepository.deleteById(id);
    }
}
