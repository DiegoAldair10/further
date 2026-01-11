package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.EmpleadoDTO;
import com.further.spring.boot.further.Entity.Empleado;
import com.further.spring.boot.further.Mapper.EmpleadosMapper;
import com.further.spring.boot.further.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadosMapper empleadosMapper;

    public List<EmpleadoDTO> obtenerTodosEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(empleadosMapper::ToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmpleadoDTO> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id)
                .map(empleadosMapper::ToDTO);
    }

    public EmpleadoDTO crearEmpleado(EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadosMapper.toEntity(empleadoDTO);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return empleadosMapper.ToDTO(empleadoGuardado);
    }

    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO empleadoDTO) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(empleadoDTO.getNombre());
                    empleado.setApellido(empleadoDTO.getApellido());
                    empleado.setEmail(empleadoDTO.getEmail());
                    empleado.setTelefono(empleadoDTO.getTelefono());
                    empleado.setCargo(empleadoDTO.getCargo());
                    return empleadoRepository.save(empleado);
                })
                .map(empleadosMapper::ToDTO)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public void eliminarEmpleado(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        empleadoRepository.deleteById(id);
    }
}