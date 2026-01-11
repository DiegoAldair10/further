package com.further.spring.boot.further.Mapper;


import com.further.spring.boot.further.Dto.DetalleVentaDTO;
import com.further.spring.boot.further.Dto.PagoDTO;
import com.further.spring.boot.further.Entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;


@Component
public class PagoMapper {

    public PagoDTO ToDTO(Pago pago) {
        if (pago == null) {
            return null;
        }

        PagoDTO dto = new PagoDTO();
        dto.setPagoId(pago.getPagoId());

        if (pago.getVenta() != null) {
            dto.setVentaId(pago.getVenta().getVentaId());
            dto.setTipoComprobante(pago.getVenta().getTipoComprobante());
            dto.setNumeroComprobante(pago.getVenta().getNumeroComprobante());

            if (pago.getVenta().getCliente() != null) {
                dto.setClienteId(pago.getVenta().getCliente().getClienteId());
                dto.setClienteNombre(pago.getVenta().getCliente().getNombre());
            }

            if (pago.getVenta().getEmpleado() != null) {
                dto.setEmpleadoId(pago.getVenta().getEmpleado().getEmpleadoId());
                dto.setEmpleadoNombre(pago.getVenta().getEmpleado().getNombre());
            }

            dto.setTotalVenta(pago.getVenta().getTotal());
            dto.setDetalles(pago.getVenta().getDetalles().stream()
                .map(detalle -> {
                    DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
                    detalleDTO.setDetalleVentaId(detalle.getDetalleVentaId());
                    detalleDTO.setProductoId(detalle.getProducto().getProductoId());
                    detalleDTO.setProductoNombre(detalle.getProducto().getNombre());
                    detalleDTO.setCantidad(detalle.getCantidad());
                    detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
                    detalleDTO.setSubtotal(detalle.getSubtotal());
                    return detalleDTO;
                })
                .collect(Collectors.toList()));
        }

        if (pago.getMetodoPago() != null) {
            dto.setMetodoPagoId(pago.getMetodoPago().getMetodoPagoId());
            dto.setNombre(pago.getMetodoPago().getNombre());
        }

        dto.setMonto(pago.getMonto());
        dto.setFecha_Pago(pago.getFecha_Pago());
        return dto;
    }

    public Pago toEntity(PagoDTO dto) {
        if (dto == null) {
            return null;
        }

        Pago pago = new Pago();
        pago.setPagoId(dto.getPagoId());

        Venta venta = new Venta();
        venta.setVentaId(dto.getVentaId());
        venta.setNumeroComprobante(dto.getNumeroComprobante());
        venta.setTipoComprobante(dto.getTipoComprobante());
        venta.setEstado(dto.getEstado());
        venta.setEstadoPago(dto.getEstadoPago());

        Cliente cliente = new Cliente();
        cliente.setClienteId(dto.getClienteId());
        venta.setCliente(cliente);

        Empleado empleado = new Empleado();
        empleado.setEmpleadoId(dto.getEmpleadoId());
        venta.setEmpleado(empleado);

        venta.setTotal(dto.getTotalVenta());
        pago.setVenta(venta);

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setNombre(dto.getNombre());
        metodoPago.setMetodoPagoId(dto.getMetodoPagoId());
        pago.setMetodoPago(metodoPago);

        pago.setMonto(dto.getMonto());
        pago.setFecha_Pago(dto.getFecha_Pago());
        return pago;
    }

}
