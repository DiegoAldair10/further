package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.DetalleVentaDTO;
import com.further.spring.boot.further.Dto.VentaDTO;
import com.further.spring.boot.further.Entity.*;
import com.further.spring.boot.further.Mapper.VentasMapper;
import com.further.spring.boot.further.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentasMapper ventaMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;



    public List<VentaDTO> obtenerTodasVentas() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VentaDTO> obtenerVentaPorId(Long id) {
        return ventaRepository.findByIdWithAllRelations(id)
                .map(ventaMapper::toDTO);
    }@Transactional
    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();

        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Empleado empleado = empleadoRepository.findById(ventaDTO.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        venta.setTipoComprobante(ventaDTO.getTipoComprobante());
        venta.setSerie(ventaDTO.getSerie());  // 👈 Guardar serie
        venta.setNumeroComprobante(ventaDTO.getNumeroComprobante());
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFechaVenta(new Date());
        venta.setMoneda(ventaDTO.getMoneda());
        venta.setEstado("BORRADOR");
        venta.setEstadoPago("PENDIENTE");

        if (ventaDTO.getDetalles() == null || ventaDTO.getDetalles().isEmpty()) {
            throw new RuntimeException("La venta debe tener al menos un detalle");
        }

        List<DetalleVenta> detalles = new ArrayList<>();
        double subTotal = 0.0;

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setVenta(venta);

            double subtotalDetalle = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
            detalle.setSubtotal(subtotalDetalle);

            subTotal += subtotalDetalle;
            detalles.add(detalle);
        }

        double igv = subTotal * 0.18;
        double total = subTotal + igv;

        venta.setDetalles(detalles);
        venta.setSubTotal(subTotal);
        venta.setIgv(igv);
        venta.setTotal(total);

        Venta ventaGuardada = ventaRepository.save(venta);

        return ventaMapper.toDTO(ventaGuardada);
    }



    public String obtenerProximoNumero(String tipoComprobante) {
        // Normalizar el tipo de comprobante
        String tipoComprobanteNormalizado = tipoComprobante.trim().toUpperCase();
        String serie = "FACTURA".equals(tipoComprobanteNormalizado) ? "F001" : "B001";

        Long correlativo = ventaRepository.countBySerie(serie) + 1; // ya lo tienes en el repo
        return String.format("%s-%06d", serie, correlativo);
    }

    @Transactional
    public void eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad()); // 🔁 Restaurar stock
            productoRepository.save(producto);
        }

        ventaRepository.delete(venta); // Elimina la venta y sus detalles (si está en cascade)
    }

    @Transactional
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Empleado empleado = empleadoRepository.findById(ventaDTO.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Restaurar stock de productos de los detalles anteriores
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Limpiar detalles anteriores
        venta.getDetalles().clear();

        // Actualizar datos de la venta
        venta.setTipoComprobante(ventaDTO.getTipoComprobante());
        venta.setSerie(ventaDTO.getSerie());
        venta.setNumeroComprobante(ventaDTO.getNumeroComprobante());
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFechaVenta(ventaDTO.getFechaVenta());
        venta.setFecha_Creacion(ventaDTO.getFecha_Creacion());
        venta.setMoneda(ventaDTO.getMoneda());
        venta.setEstado(ventaDTO.getEstado());
        venta.setEstadoPago(ventaDTO.getEstadoPago());

        List<DetalleVenta> nuevosDetalles = new ArrayList<>();
        double subTotal = 0.0;

        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setVenta(venta);

            double subtotalDetalle = detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
            detalle.setSubtotal(subtotalDetalle);

            subTotal += subtotalDetalle;
            nuevosDetalles.add(detalle);
        }

        double igv = subTotal * 0.18;
        double total = subTotal + igv;

        venta.setDetalles(nuevosDetalles);
        venta.setSubTotal(subTotal);
        venta.setIgv(igv);
        venta.setTotal(total);

        Venta ventaActualizada = ventaRepository.save(venta);

        return ventaMapper.toDTO(ventaActualizada);
    }

}
