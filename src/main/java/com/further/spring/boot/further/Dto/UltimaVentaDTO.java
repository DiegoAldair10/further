package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UltimaVentaDTO {

    private String cliente;

    private String comprobante;

    private Date fecha;

    private Double total;

    private String estado;

}