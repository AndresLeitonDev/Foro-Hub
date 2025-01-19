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

import com.alura.Foro_Hub.models.Usuario;
import com.alura.Foro_Hub.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(originPatterns = "*")
public class UsuarioController {

    @Autowired
    private CentralMethods centralMethods;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Usuario> opt = usuarioService.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.orElseThrow());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            if (usuario.getPerfiles() == null || usuario.getPerfiles().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El usuario debe tener al menos un perfil asociado");
            } else {
                List<Usuario> listUsernames = usuarioService.findByNombre(usuario.getNombre());
                List<Usuario> listEmails = usuarioService.findByEmail(usuario.getEmail());

                if (!listUsernames.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("El nombre de usuario ya se encuentra registrado");
                } else if (!listEmails.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("El email del usuario ya se encuentra registrado");
                } else {
                    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return centralMethods.validation(result);
        }
        try {
            Optional<Usuario> opt = usuarioService.findById(id);
            if (opt.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.update(usuario, id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Usuario> opt = usuarioService.findById(id);
            if (opt.isPresent()) {
                usuarioService.remove(id);
                return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
