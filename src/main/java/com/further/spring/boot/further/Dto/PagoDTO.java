package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PagoDTO {
    private Long pagoId;
    private Long ventaId;
    private Long clienteId;
    private String clienteNombre;
    private Long empleadoId;
    private String empleadoNombre;
    private Double totalVenta;
    private String tipoComprobante;
    private String numeroComprobante;
    private List<DetalleVentaDTO> detalles;
    private Long metodoPagoId;
    private String nombre;
    private Double monto;
    private String estado;
    private String estadoPago;
    private Date fecha_Pago;
}