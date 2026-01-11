package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_clientes")
    @SequenceGenerator(name = "seq_clientes", sequenceName = "Seq_Clientes", allocationSize = 1)
    @Column(name = "ClienteID")
    private Long clienteId;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Pais")
    private String pais;

    @Column(name = "tipo_documento")
    private String documento;

    @Column(name = "Numero_Documento")
    private String numeroDocumento;

    @Column(name = "fecha_Registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void asignarFechaRegistro() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}
