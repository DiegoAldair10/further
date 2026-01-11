package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.MetodoPagoDTO;
import com.further.spring.boot.further.Entity.MetodoPago;
import com.further.spring.boot.further.Mapper.MetodoPagoMapper;
import com.further.spring.boot.further.Repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private MetodoPagoMapper metodoPagoMapper;

    public List<MetodoPagoDTO> obtenerTodosMetodosPago() {
        return metodoPagoRepository.findAll().stream()
                .map(metodoPagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MetodoPagoDTO> obtenerMetodoPagoPorId(Long id) {
        return metodoPagoRepository.findById(id)
                .map(metodoPagoMapper::toDTO);
    }

    public MetodoPagoDTO crearMetodoPago(MetodoPagoDTO metodoPagoDTO) {
        MetodoPago metodoPago = metodoPagoMapper.toEntity(metodoPagoDTO);
        MetodoPago metodoPagoGuardado = metodoPagoRepository.save(metodoPago);
        return metodoPagoMapper.toDTO(metodoPagoGuardado);
    }

    public MetodoPagoDTO actualizarMetodoPago(Long id, MetodoPagoDTO metodoPagoDTO) {
        return metodoPagoRepository.findById(id)
                .map(metodoPago -> {
                    metodoPago.setNombre(metodoPagoDTO.getNombre());
                    metodoPago.setDescripcion(metodoPagoDTO.getDescripcion());
                    return metodoPagoRepository.save(metodoPago);
                })
                .map(metodoPagoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
    }

    public void eliminarMetodoPago(Long id) {
        if (!metodoPagoRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        metodoPagoRepository.deleteById(id);
    }
}