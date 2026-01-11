package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.PagoDTO;
import com.further.spring.boot.further.Entity.MetodoPago;
import com.further.spring.boot.further.Entity.Pago;
import com.further.spring.boot.further.Entity.Venta;
import com.further.spring.boot.further.Mapper.PagoMapper;
import com.further.spring.boot.further.Repository.MetodoPagoRepository;
import com.further.spring.boot.further.Repository.PagoRepository;
import com.further.spring.boot.further.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private PagoMapper pagoMapper;


    public List<PagoDTO> obtenerTodosPagos() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::ToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PagoDTO> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .map(pagoMapper ::ToDTO);
    }

    public PagoDTO crearPago(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        Venta venta = ventaRepository.findById(pagoDTO.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        MetodoPago metodoPago = metodoPagoRepository.findById(pagoDTO.getMetodoPagoId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        pago.setVenta(venta);
        pago.setMetodoPago(metodoPago);
        pago.setMonto(pagoDTO.getMonto());
        pago.setFecha_Pago(new Date()); // Se asigna automáticamente aquí

        Pago pagoGuardado = pagoRepository.save(pago);
        // Cambiar estado de la venta a "PAGADA" y guardar
        venta.setEstadoPago("PAGADA");
        venta.setEstado("EMITIDA");
        ventaRepository.save(venta);
        return pagoMapper.ToDTO(pagoGuardado);
    }

    public void eliminarPago(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        pagoRepository.deleteById(id);
    }
}
