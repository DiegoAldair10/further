package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.EmpleadoDTO;
import com.further.spring.boot.further.Entity.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadosMapper {

    public EmpleadoDTO ToDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setEmpleadoId(empleado.getEmpleadoId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getTelefono());
        dto.setCargo(empleado.getCargo());
        dto.setFecha_Contratacion(empleado.getFecha_Contratacion());
        return dto;
    }


    public Empleado toEntity(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setEmpleadoId(dto.getEmpleadoId());
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setTelefono(dto.getTelefono());
        empleado.setCargo(dto.getCargo());
        empleado.setFecha_Contratacion(dto.getFecha_Contratacion());
        return empleado;
    }


}
