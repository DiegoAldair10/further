package com.further.spring.boot.further.Dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
