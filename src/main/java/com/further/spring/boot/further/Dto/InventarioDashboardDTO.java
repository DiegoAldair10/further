package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class InventarioDashboardDTO {

    private Double stockActual;

    private Double entradasHoy;

    private Double salidasHoy;

    private Long productosStockBajo;

}
