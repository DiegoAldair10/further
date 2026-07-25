package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PerfilDTO {

    private Long usuarioId;

    private Long empleadoId;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String cargo;

    private Integer estado;

    private List<String> roles;
}