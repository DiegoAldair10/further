package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.ProveedorDTO;
import com.further.spring.boot.further.Entity.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {
    public ProveedorDTO toDTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setProveedorId(proveedor.getProveedorId());
        dto.setNombre(proveedor.getNombre());
        dto.setRuc(proveedor.getRuc());
        dto.setContacto(proveedor.getContacto());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setDireccion(proveedor.getDireccion());
        return dto;
    }



    public Proveedor toEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setProveedorId(dto.getProveedorId());
        proveedor.setNombre(dto.getNombre());
        proveedor.setRuc(dto.getRuc());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        proveedor.setDireccion(dto.getDireccion());
        return proveedor;
    }



}
