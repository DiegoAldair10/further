package com.further.spring.boot.further.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EMPLEADOS")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empleados")
    @SequenceGenerator(name = "seq_empleados", sequenceName = "Seq_Empleados", allocationSize = 1)
    @Column(name = "EMPLEADOID")
    private Long empleadoId;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Cargo")
    private String cargo;


    @Column(name = "FECHA_CONTRATACION")
    private LocalDateTime fecha_Contratacion;

    @PrePersist
    public void asignarFechaContratacion() {
        if (this.fecha_Contratacion == null) {
            this.fecha_Contratacion = LocalDateTime.now();
        }
    }
}