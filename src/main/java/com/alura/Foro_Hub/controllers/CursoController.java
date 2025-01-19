package com.alura.Foro_Hub.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.Foro_Hub.models.Curso;
import com.alura.Foro_Hub.services.CursoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/curso")
@CrossOrigin(originPatterns = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CentralMethods centralMethods;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Curso> cursos = cursoService.findAll();
        if(cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay cursos registrados");
        }else{
            return ResponseEntity.ok(cursos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Curso> opt = cursoService.findById(id);
        if(opt.isPresent()){
            return ResponseEntity.ok(opt.orElseThrow());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Optional<Curso> opt = cursoService.findById(id);
            if(opt.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.update(curso, id));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Curso> opt = cursoService.findById(id);
            if(opt.isPresent()){
                cursoService.remove(id);
                return ResponseEntity.status(HttpStatus.OK).body("Curso eliminado");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
