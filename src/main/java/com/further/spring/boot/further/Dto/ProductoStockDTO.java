package com.further.spring.boot.further.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoStockDTO{

    private Long productoId;
    private String nombre;
    private Integer stock;

}