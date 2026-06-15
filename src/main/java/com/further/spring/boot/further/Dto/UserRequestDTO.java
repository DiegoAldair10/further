package com.further.spring.boot.further.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDTO {

    private String email;
    private String password;
    private Integer estado;
    private Set<String> roles;
}