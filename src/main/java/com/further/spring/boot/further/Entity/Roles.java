package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROLES")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles")
    @SequenceGenerator(name = "seq_roles", sequenceName = "seq_roles", allocationSize = 1)
    @Column(name = "ROLESID")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
