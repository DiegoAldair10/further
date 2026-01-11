package com.further.spring.boot.further.Exception;

public class ClienteYaExisteException extends RuntimeException {
    public ClienteYaExisteException(String mensaje) {
        super(mensaje);
    }
}