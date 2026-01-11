package com.further.spring.boot.further.Dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmpleadoDTO {
    private Long empleadoId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String cargo;
    private LocalDateTime fecha_Contratacion;
}