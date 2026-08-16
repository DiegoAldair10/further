package com.further.spring.boot.further.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockCategoriaDTO {

    private String categoria;

    private Integer stock;

}