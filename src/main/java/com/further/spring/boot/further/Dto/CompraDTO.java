package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class CompraDTO {
    private Long compraId;
    private Long proveedorId;
    private String proveedor;
    private Date fechaCompra;
    private String tipoComprobante;
    private String serie;
    private String numero;
    private String moneda;
    private Double subtotal;
    private Double igv;
    private Double totalCompra;
    private String estado;
    private String estadoPago;
    private List<DetalleCompraDTO> detalles;
}