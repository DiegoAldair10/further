package com.further.spring.boot.further.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private String descripcion;
    private Long categoriaId;
    private Double precio_venta;
    private Double costo_promedio;
    private Integer stock;
    private String estado;
    private LocalDateTime fecha_Creacion;
    private List<Long> detallesIds; // O List<DetalleVentaDTO> si tienes un DTO para DetalleVenta
}