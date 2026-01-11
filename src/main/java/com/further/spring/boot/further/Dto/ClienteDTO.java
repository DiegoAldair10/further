package com.further.spring.boot.further.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String pais;
    private String documento;
    private String numeroDocumento;
    private LocalDateTime fechaRegistro;
}
