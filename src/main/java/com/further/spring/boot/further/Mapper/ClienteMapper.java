package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.ClienteDTO;
import com.further.spring.boot.further.Entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {


    public ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setCiudad(cliente.getCiudad());
        dto.setPais(cliente.getPais());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setDocumento(cliente.getDocumento());
        dto.setNumeroDocumento(cliente.getNumeroDocumento());
        return dto;
    }


    public Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(dto.getClienteId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setCiudad(dto.getCiudad());
        cliente.setPais(dto.getPais());
        cliente.setFechaRegistro(dto.getFechaRegistro());
        cliente.setDocumento(dto.getDocumento());
        cliente.setNumeroDocumento(dto.getNumeroDocumento());
        return cliente;
    }




}
