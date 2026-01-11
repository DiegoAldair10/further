package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CATEGORIAS")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORIAS")
    @SequenceGenerator(name = "SEQ_CATEGORIAS", sequenceName = "SEQ_CATEGORIAS", allocationSize = 1)
    @Column(name = "CATEGORIAID")
    private Long categoriaId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fecha_creacion;

    @PrePersist
    public void asignarfecha_creacion() {
        if (this.fecha_creacion == null) {
            this.fecha_creacion = LocalDateTime.now();
        }
    }
}
