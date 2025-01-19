package com.alura.Foro_Hub.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alura.Foro_Hub.models.Topico;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/topico")
@CrossOrigin(originPatterns = "*")
public class TopicController {
    
    @Autowired
    private CentralMethods centralMethods;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Topico topico, BindingResult result) { // RequestBody El                                                                                    // body de la peticion
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        /*try {
            return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.save(org));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }*/
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }
}
