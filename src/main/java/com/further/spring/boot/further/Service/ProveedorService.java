package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.ProveedorDTO;
import com.further.spring.boot.further.Entity.Proveedor;
import com.further.spring.boot.further.Mapper.ProveedorMapper;
import com.further.spring.boot.further.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;

    public List<ProveedorDTO> obtenerTodosProveedores() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper ::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProveedorDTO> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id)
                .map(proveedorMapper ::toDTO);
    }

    public ProveedorDTO crearProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
        return proveedorMapper.toDTO(proveedorGuardado);
    }

    public ProveedorDTO actualizarProveedor(Long id, ProveedorDTO proveedorDTO) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorDTO.getNombre());
                    proveedor.setRuc(proveedorDTO.getRuc());
                    proveedor.setContacto(proveedorDTO.getContacto());
                    proveedor.setTelefono(proveedorDTO.getTelefono());
                    proveedor.setEmail(proveedorDTO.getEmail());
                    proveedor.setDireccion(proveedorDTO .getDireccion());
                    return proveedorRepository.save(proveedor);
                })
                .map(proveedorMapper ::toDTO)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)){
            throw new RuntimeException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }
}