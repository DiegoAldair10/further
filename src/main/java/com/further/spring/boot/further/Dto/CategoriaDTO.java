package com.further.spring.boot.further.Dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoriaDTO {
    private Long categoriaId;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha_creacion;

}
