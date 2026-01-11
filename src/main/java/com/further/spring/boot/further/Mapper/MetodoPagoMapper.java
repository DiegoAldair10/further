package com.further.spring.boot.further.Mapper;


import com.further.spring.boot.further.Dto.MetodoPagoDTO;
import com.further.spring.boot.further.Entity.MetodoPago;
import org.springframework.stereotype.Component;

@Component
public class MetodoPagoMapper {


    public MetodoPagoDTO toDTO(MetodoPago metodoPago) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setMetodoPagoId(metodoPago.getMetodoPagoId());
        dto.setNombre(metodoPago.getNombre());
        dto.setDescripcion(metodoPago.getDescripcion());
        return dto;
    }


    public MetodoPago toEntity(MetodoPagoDTO dto) {
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setMetodoPagoId(dto.getMetodoPagoId());
        metodoPago.setNombre(dto.getNombre());
        metodoPago.setDescripcion(dto.getDescripcion());
        return metodoPago;
    }


}
