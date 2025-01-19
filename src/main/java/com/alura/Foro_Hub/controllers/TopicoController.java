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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.Foro_Hub.models.Topico;
import com.alura.Foro_Hub.services.TopicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topico")
@CrossOrigin(originPatterns = "*")
public class TopicoController {

    @Autowired
    private CentralMethods centralMethods;

    @Autowired
    private TopicoService topicoService;


    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        List<Topico> topicos = topicoService.findAll();
        if(topicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tópicos registrados");
        }else{
            return ResponseEntity.ok(topicos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopicsById(@PathVariable Long id) {
        Optional<Topico> opt = topicoService.findById(id);
        if(opt.isPresent()){
            return ResponseEntity.ok(opt.orElseThrow());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
        }
    }

    @GetMapping("/curso/{curso}")
    public ResponseEntity<?> getTopicsByCourse(@PathVariable String curso) {
        List<Topico> topicos = topicoService.findByNombreCurso(curso);
        if(topicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tópicos registrados para este curso");
        }else{
            return ResponseEntity.ok(topicos);
        }
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<?> getTopicsByYear(@PathVariable String anio) {
        List<Topico> topicos = topicoService.findByYear(anio);
        if(topicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tópicos registrados para este año");
        }else{
            return ResponseEntity.ok(topicos);
        }
    }

    @GetMapping("/order")
    public ResponseEntity<?> getTopicsByOrder() {
        List<Topico> topicos = topicoService.findOrderByFechaCreacionDesc();
        if(topicos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay tópicos registrados");
        }else{
            return ResponseEntity.ok(topicos);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Topico topico, BindingResult result) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            List<Topico> listTitles = topicoService.findByTitulo(topico.getTitulo());
            List<Topico> listMensajes = topicoService.findByMensaje(topico.getMensaje());
            if(!listTitles.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El título del tópico ya existe");
            }else if(!listMensajes.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El mensaje del tópico ya existe");
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body(topicoService.save(topico));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Topico topico, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Optional<Topico> opt = topicoService.findById(id);
            if(opt.isPresent()){
                List<Topico> listTitles = topicoService.findByTitulo(topico.getTitulo());
                List<Topico> listMensajes = topicoService.findByMensaje(topico.getMensaje());
                if(!listTitles.isEmpty()){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("El título del tópico ya existe");
                }else if(!listMensajes.isEmpty()){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("El mensaje del tópico ya existe");
                }else{
                    return ResponseEntity.status(HttpStatus.CREATED).body(topicoService.update(topico, id));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Topico> opt = topicoService.findById(id);
            if(opt.isPresent()){
                topicoService.remove(id);
                return ResponseEntity.status(HttpStatus.OK).body("Tópico eliminado");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
