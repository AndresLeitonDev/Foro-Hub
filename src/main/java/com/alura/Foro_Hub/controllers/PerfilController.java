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

import com.alura.Foro_Hub.models.Perfil;
import com.alura.Foro_Hub.services.PerfilService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfil")
@CrossOrigin(originPatterns = "*")
public class PerfilController {

    @Autowired
    private CentralMethods centralMethods;

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<?> getAllProfiles() {
        List<Perfil> perfiles = perfilService.findAll();
        if(perfiles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay perfiles registrados");
        }else{
            return ResponseEntity.ok(perfiles);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        Optional<Perfil> opt = perfilService.findById(id);
        if(opt.isPresent()){
            return ResponseEntity.ok(opt.orElseThrow());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Perfil perfil, BindingResult result) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            List<Perfil> listNames = perfilService.findByNombre(perfil.getNombre());
            if(!listNames.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de perfil ya existe");
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.save(perfil));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Perfil perfil, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Optional<Perfil> opt = perfilService.findById(id);
            if(opt.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.update(perfil, id));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("perfil no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Perfil> opt = perfilService.findById(id);
            if(opt.isPresent()){
                perfilService.remove(id);
                return ResponseEntity.status(HttpStatus.OK).body("Perfil eliminado");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
