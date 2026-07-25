package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class ActualizarPerfilDTO {

    private String nombre;

    private String apellido;

    private String telefono;

    private String email;
}