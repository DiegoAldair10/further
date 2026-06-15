package com.further.spring.boot.further.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AuthResponse {
    private String token;
    private String email;
    private List<String> roles;
}