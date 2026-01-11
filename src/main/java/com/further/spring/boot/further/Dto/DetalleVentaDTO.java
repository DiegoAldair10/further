package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long detalleVentaId;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}