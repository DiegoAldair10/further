package com.further.spring.boot.further.Dto;


import lombok.Data;

@Data

public class ProveedorDTO {
    private Long proveedorId;
    private String nombre;
    private String contacto;
    private String telefono;
    private String ruc;
    private String email;
    private String direccion;
}
