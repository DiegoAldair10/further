package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class DetalleCompraDTO {

    private Long detalleCompraId;
    private Long productoId;
    private String producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

}