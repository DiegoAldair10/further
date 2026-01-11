package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PROVEEDORES")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proveedores")
    @SequenceGenerator(name = "seq_proveedores", sequenceName = "Seq_Proveedores", allocationSize = 1)
    @Column(name = "PROVEEDORID")
    private Long proveedorId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "RUC")
    private String ruc;

    @Column(name = "CONTACTO")
    private String contacto;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DIRECCION")
    private String direccion;
}
