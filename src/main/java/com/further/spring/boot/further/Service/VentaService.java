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

    @Autowired
    private PagoRepository pagoRepository;


    public List<VentaDTO> obtenerTodasVentas() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VentaDTO> obtenerVentaPorId(Long id) {
        return ventaRepository.findByIdWithAllRelations(id)
                .map(ventaMapper::toDTO);
    }

    @Transactional
    public VentaDTO crearVenta(VentaDTO ventaDTO) {

        Venta venta = new Venta();

        Cliente cliente = clienteRepository.findById(
                        ventaDTO.getClienteId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        Empleado empleado = empleadoRepository.findById(
                        ventaDTO.getEmpleadoId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Empleado no encontrado"));

        venta.setTipoComprobante(
                ventaDTO.getTipoComprobante()
        );

        venta.setSerie(
                ventaDTO.getSerie()
        );

        venta.setNumeroComprobante(
                ventaDTO.getNumeroComprobante()
        );

        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFechaVenta(new Date());
        venta.setMoneda(
                ventaDTO.getMoneda()
        );

        venta.setEstado("BORRADOR");
        venta.setEstadoPago("PENDIENTE");

        if (
                ventaDTO.getDetalles() == null ||
                        ventaDTO.getDetalles().isEmpty()
        ) {
            throw new RuntimeException(
                    "La venta debe tener al menos un detalle"
            );
        }

        List<DetalleVenta> detalles =
                new ArrayList<>();

        double subTotal = 0.0;

        for (
                DetalleVentaDTO detalleDTO :
                ventaDTO.getDetalles()
        ) {

            Producto producto =
                    productoRepository.findById(
                                    detalleDTO.getProductoId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Producto no encontrado"
                                    ));

            if (detalleDTO.getCantidad() <= 0) {
                throw new RuntimeException(
                        "La cantidad debe ser mayor a cero"
                );
            }

            if (
                    producto.getStock() <
                            detalleDTO.getCantidad()
            ) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: "
                                + producto.getNombre()
                );
            }

            producto.setStock(
                    producto.getStock()
                            - detalleDTO.getCantidad()
            );

            productoRepository.save(producto);

            DetalleVenta detalle =
                    new DetalleVenta();

            detalle.setProducto(producto);

            detalle.setCantidad(
                    detalleDTO.getCantidad()
            );

            double precioVenta =
                    producto.getPrecio_venta();

            detalle.setPrecioUnitario(
                    precioVenta
            );

            double subtotalDetalle =
                    detalleDTO.getCantidad()
                            * precioVenta;

            detalle.setSubtotal(
                    subtotalDetalle
            );

            detalle.setVenta(venta);

            subTotal += subtotalDetalle;

            detalles.add(detalle);
        }

        double igv =
                Math.round(
                        (subTotal * 0.18) * 100.0
                ) / 100.0;

        double total =
                Math.round(
                        (subTotal + igv) * 100.0
                ) / 100.0;

        venta.setDetalles(detalles);
        venta.setSubTotal(subTotal);
        venta.setIgv(igv);
        venta.setTotal(total);
        Venta ventaGuardada =
                ventaRepository.save(venta);
        return ventaMapper.toDTO(
                ventaGuardada
        );

    }


    public String obtenerProximoNumero(String tipoComprobante) {
        String tipoNormalizado = tipoComprobante.trim().toUpperCase();
        String serie = "FACTURA".equals(tipoNormalizado)
                ? "F001"
                : "B001";
        String ultimoNumero = ventaRepository.obtenerUltimoNumeroPorSerie(serie);
        long correlativo = 1;
        if (ultimoNumero != null) {
            correlativo = Long.parseLong(ultimoNumero) + 1;
        }
        return String.format("%s-%06d", serie, correlativo);
    }


    @Transactional
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Venta no encontrada"
                        ));

        Cliente cliente = clienteRepository.findById(
                        ventaDTO.getClienteId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente no encontrado"
                        ));

        Empleado empleado = empleadoRepository.findById(
                        ventaDTO.getEmpleadoId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Empleado no encontrado"
                        ));

        // Restaurar stock anterior
        for (DetalleVenta detalle :
                new ArrayList<>(venta.getDetalles())) {

            Producto producto =
                    detalle.getProducto();

            producto.setStock(
                    producto.getStock()
                            + detalle.getCantidad()
            );

            productoRepository.save(producto);
        }

        venta.getDetalles().clear();

        venta.setTipoComprobante(
                ventaDTO.getTipoComprobante()
        );

        venta.setSerie(
                ventaDTO.getSerie()
        );

        venta.setNumeroComprobante(
                ventaDTO.getNumeroComprobante()
        );

        venta.setCliente(cliente);

        venta.setEmpleado(empleado);

        venta.setFechaVenta(
                ventaDTO.getFechaVenta()
        );

        venta.setFecha_Creacion(
                ventaDTO.getFecha_Creacion()
        );

        venta.setMoneda(
                ventaDTO.getMoneda()
        );

        venta.setEstado(
                ventaDTO.getEstado()
        );

        venta.setEstadoPago(
                ventaDTO.getEstadoPago()
        );

        double subTotal = 0.0;

        for (DetalleVentaDTO detalleDTO :
                ventaDTO.getDetalles()) {

            Producto producto =
                    productoRepository.findById(
                                    detalleDTO.getProductoId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Producto no encontrado"
                                    ));

            if (detalleDTO.getCantidad() <= 0) {

                throw new RuntimeException(
                        "La cantidad debe ser mayor a cero"
                );
            }

            if (
                    producto.getStock()
                            < detalleDTO.getCantidad()
            ) {

                throw new RuntimeException(
                        "Stock insuficiente para el producto: "
                                + producto.getNombre()
                );
            }

            producto.setStock(
                    producto.getStock()
                            - detalleDTO.getCantidad()
            );

            productoRepository.save(producto);

            double precioVenta =
                    producto.getPrecio_venta();

            double subtotalDetalle =
                    detalleDTO.getCantidad()
                            * precioVenta;

            DetalleVenta detalle =
                    new DetalleVenta();

            detalle.setProducto(producto);

            detalle.setCantidad(
                    detalleDTO.getCantidad()
            );

            detalle.setPrecioUnitario(
                    precioVenta
            );

            detalle.setSubtotal(
                    subtotalDetalle
            );

            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);

            subTotal += subtotalDetalle;
        }

        double igv =
                Math.round(
                        (subTotal * 0.18) * 100.0
                ) / 100.0;

        double total =
                Math.round(
                        (subTotal + igv) * 100.0
                ) / 100.0;

        venta.setSubTotal(subTotal);

        venta.setIgv(igv);

        venta.setTotal(total);

        System.out.println(
                "ACTUALIZAR VENTA -> ESTADO = "
                        + venta.getEstado()
        );

        System.out.println(
                "ACTUALIZAR VENTA -> ESTADO_PAGO = "
                        + venta.getEstadoPago()
        );

        Venta ventaActualizada =
                ventaRepository.save(venta);

        return ventaMapper.toDTO(
                ventaActualizada
        );
    }

    @Transactional
    public void eliminarVenta(Long id) {

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Venta no encontrada"
                        ));

        // NO ELIMINAR SI TIENE PAGOS
        if (
                "PAGADA".equals(venta.getEstadoPago())
                        ||
                        "PARCIAL".equals(venta.getEstadoPago())
        ) {

            throw new RuntimeException(
                    "No se puede eliminar una venta con pagos registrados"
            );
        }

        // RESTAURAR STOCK

        for (
                DetalleVenta detalle :
                new ArrayList<>(venta.getDetalles())
        ) {

            Producto producto =
                    detalle.getProducto();

            producto.setStock(
                    producto.getStock()
                            + detalle.getCantidad()
            );

            productoRepository.save(producto);
        }

        // ELIMINAR DETALLES

        venta.getDetalles().clear();

        // ELIMINAR VENTA
    
        ventaRepository.delete(venta);
    }
}
