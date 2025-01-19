package com.alura.Foro_Hub.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topico_id")
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensaje;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @NotBlank
    private String status;

    @ManyToOne
    @JoinTable(name = "topico_join_curso", 
            joinColumns = @JoinColumn(name = "topico_id"), 
            inverseJoinColumns = @JoinColumn(name = "curso_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "topico_id", "curso_id" }) })
    private Curso curso;

    @ManyToOne
    @JoinTable(name = "topico_join_usuario", 
            joinColumns = @JoinColumn(name = "topico_id"), 
            inverseJoinColumns = @JoinColumn(name = "usuario_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "topico_id", "usuario_id" }) })
    private Usuario autor;

    @OneToMany
    @JoinTable(name = "topico_join_respuesta", 
            joinColumns = @JoinColumn(name = "topico_id"), 
            inverseJoinColumns = @JoinColumn(name = "respuesta_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "topico_id", "respuesta_id" }) })
    private List<Respuesta> respuestas;

    public Topico() {
    }

    public Topico(String titulo, String mensaje, Date fechaCreacion, String status, Usuario autor) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }  

}
