package com.alura.Foro_Hub.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "respuesta_id")
    private Long id;

    @NotBlank
    private String mensaje;

    @ManyToOne
    @JoinTable(name = "respuesta_join_topico", 
            joinColumns = @JoinColumn(name = "respuesta_id"), 
            inverseJoinColumns = @JoinColumn(name = "topico_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "respuesta_id", "topico_id" }) })
    private Topico topico;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne
    @JoinTable(name = "respuesta_join_usuario", 
            joinColumns = @JoinColumn(name = "respuesta_id"), 
            inverseJoinColumns = @JoinColumn(name = "usuario_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "respuesta_id", "usuario_id" }) })
    private Usuario autor;

    @NotBlank
    private String solucion;

    public Respuesta() {
    }

    public Respuesta(String mensaje, Topico topico, Date fechaCreacion, Usuario autor, String solucion) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = fechaCreacion;
        this.autor = autor;
        this.solucion = solucion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

}
