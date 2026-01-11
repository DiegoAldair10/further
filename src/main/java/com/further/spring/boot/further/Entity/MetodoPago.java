package com.further.spring.boot.further.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "METODOSPAGO")
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_METODOSPAGO")
    @SequenceGenerator(name = "SEQ_METODOSPAGO", sequenceName = "SEQ_METODOSPAGO", allocationSize = 1)
    @Column(name = "METODOPAGOID")
    private Long metodoPagoId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;
}
