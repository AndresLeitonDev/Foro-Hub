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

import com.alura.Foro_Hub.models.Respuesta;
import com.alura.Foro_Hub.services.RespuestaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuesta")
@CrossOrigin(originPatterns = "*")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private CentralMethods centralMethods;

    @GetMapping
    public ResponseEntity<?> getAllResponses() {
        List<Respuesta> respuestas = respuestaService.findAll();
        if (respuestas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay respuestas registradas");
        } else {
            return ResponseEntity.ok(respuestas);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResponseById(@PathVariable Long id) {
        Optional<Respuesta> opt = respuestaService.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.orElseThrow());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Respuesta respuesta, BindingResult result) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            if (respuesta.getTopico() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo topico es obligatorio");
            } else if (respuesta.getAutor() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo autor es obligatorio");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.save(respuesta));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Respuesta respuesta, BindingResult result,
            @PathVariable Long id) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Optional<Respuesta> opt = respuestaService.findById(id);
            if (opt.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.update(respuesta, id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Respuesta> opt = respuestaService.findById(id);
            if (opt.isPresent()) {
                respuestaService.remove(id);
                return ResponseEntity.status(HttpStatus.OK).body("Respuesta eliminada");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Respuesta no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
