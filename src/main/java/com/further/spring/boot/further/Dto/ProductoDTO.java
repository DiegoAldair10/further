package com.further.spring.boot.further.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private List<Long> detallesIds;

    public ProductoDTO(Long productoId, String nombre) {
        this.productoId = productoId;
        this.nombre = nombre;
    }
}