package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.DetalleVentaDTO;
import com.further.spring.boot.further.Dto.VentaDTO;
import com.further.spring.boot.further.Entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentasMapper {

    public VentaDTO toDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setVentaId(venta.getVentaId());
        dto.setClienteId(venta.getCliente().getClienteId());
        dto.setClienteNombre(venta.getCliente().getNombre());
        dto.setEmpleadoId(venta.getEmpleado().getEmpleadoId());
        dto.setEmpleadoNombre(venta.getEmpleado().getNombre());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTipoComprobante(venta.getTipoComprobante());
        dto.setSerie(venta.getSerie());
        dto.setNumeroComprobante(venta.getNumeroComprobante());
        dto.setMoneda(venta.getMoneda());
        dto.setSubTotal(venta.getSubTotal());
        dto.setIgv(venta.getIgv());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());
        dto.setEstadoPago(venta.getEstadoPago());
        dto.setFecha_Creacion(venta.getFecha_Creacion());

        if (venta.getDetalles() != null) {
            dto.setDetalles(
                    venta.getDetalles().stream()
                            .map(this::mapToDetalleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Venta toEntity(VentaDTO dto) {
        Venta venta = new Venta();
        venta.setVentaId(dto.getVentaId());

        Cliente cliente = new Cliente();
        cliente.setClienteId(dto.getClienteId());
        venta.setCliente(cliente);

        Empleado empleado = new Empleado();
        empleado.setEmpleadoId(dto.getEmpleadoId());
        venta.setEmpleado(empleado);

        venta.setFechaVenta(dto.getFechaVenta());
        venta.setTipoComprobante(dto.getTipoComprobante());
        venta.setSerie(dto.getSerie());
        venta.setNumeroComprobante(dto.getNumeroComprobante());
        venta.setMoneda(dto.getMoneda());
        venta.setSubTotal(dto.getSubTotal());
        venta.setIgv(dto.getIgv());
        venta.setTotal(dto.getTotal());
        venta.setEstado(dto.getEstado());
        venta.setEstadoPago(dto.getEstadoPago());
        venta.setFecha_Creacion(dto.getFecha_Creacion());

        if (dto.getDetalles() != null) {
            List<DetalleVenta> detalles = dto.getDetalles().stream()
                    .map(d -> mapToDetalleEntity(d, venta))
                    .collect(Collectors.toList());
            venta.setDetalles(detalles);
        }

        return venta;
    }

    // Métodos auxiliares reutilizables

    private DetalleVentaDTO mapToDetalleDTO(DetalleVenta d) {
        DetalleVentaDTO ddto = new DetalleVentaDTO();
        ddto.setDetalleVentaId(d.getDetalleVentaId());
        ddto.setProductoId(d.getProducto().getProductoId());
        ddto.setProductoNombre(d.getProducto().getNombre());
        ddto.setCantidad(d.getCantidad());
        ddto.setPrecioUnitario(d.getPrecioUnitario());
        ddto.setSubtotal(d.getSubtotal());
        return ddto;
    }

    private DetalleVenta mapToDetalleEntity(DetalleVentaDTO d, Venta venta) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setDetalleVentaId(d.getDetalleVentaId());

        Producto producto = new Producto();
        producto.setProductoId(d.getProductoId());
        detalle.setProducto(producto);

        detalle.setCantidad(d.getCantidad());
        detalle.setPrecioUnitario(d.getPrecioUnitario());
        detalle.setVenta(venta);

        return detalle;
    }
}
