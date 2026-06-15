package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "estado")
    private Integer estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USUARIO_ROLES",
            joinColumns = @JoinColumn(name = "USUARIOSID"),
            inverseJoinColumns = @JoinColumn(name = "ROLESID")
    )
    private Set<Roles> roles = new HashSet<>();

}
