package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIOS")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuarios")
    @SequenceGenerator(name = "seq_usuarios", sequenceName = "seq_usuarios", allocationSize = 1)
    @Column(name = "USUARIOSID")
    private Long usuariosId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "rol")
    private String rol;

    @Column(name = "estado")
    private Integer estado;
}
