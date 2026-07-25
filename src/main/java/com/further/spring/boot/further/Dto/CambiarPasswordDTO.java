package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class CambiarPasswordDTO {

    private String passwordActual;

    private String nuevaPassword;

    private String confirmarPassword;
}