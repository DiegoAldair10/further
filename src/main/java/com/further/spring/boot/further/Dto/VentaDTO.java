package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VentaDTO {
    private Long ventaId;
    private Long clienteId;
    private String clienteNombre;
    private Long empleadoId;
    private String empleadoNombre;
    private Date fechaVenta;
    private String tipoComprobante;
    private String serie;
    private String numeroComprobante;
    private String moneda;
    private Double subTotal;
    private Double igv;
    private Double total;
    private String estado;
    private String estadoPago;
    private Date fecha_Creacion;
    private List<DetalleVentaDTO> detalles;

}
