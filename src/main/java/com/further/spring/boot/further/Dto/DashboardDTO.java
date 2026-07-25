package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private Long totalProductos;
    private Long totalClientes;
    private Long totalVentas;
    private Double montoVentas;
    private Long totalUsuarios;
}
