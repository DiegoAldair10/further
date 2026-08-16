package com.further.spring.boot.further.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KardexDTO {

    private String producto;

    private String tipo;

    private Integer cantidad;

    private Integer stockAnterior;

    private Integer stockNuevo;

    private LocalDateTime fecha;

    private Long movId;

    private Long productoId;

    private String tipoMov;

    private String origen;

    private String observacion;

    public KardexDTO(Long movId,
                     Long productoId,
                     String producto,
                     LocalDateTime fecha,
                     String tipoMov,
                     String origen,
                     Integer cantidad,
                     Integer stockAnterior,
                     Integer stockNuevo,
                     String observacion) {

        this.movId = movId;
        this.productoId = productoId;
        this.producto = producto;
        this.fecha = fecha;
        this.tipoMov = tipoMov;

        // Si tu frontend usa el campo "tipo", lo dejamos igual al tipo de movimiento.
        this.tipo = tipoMov;

        this.origen = origen;
        this.cantidad = cantidad;
        this.stockAnterior = stockAnterior;
        this.stockNuevo = stockNuevo;
        this.observacion = observacion;
    }
}