package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.CompraDTO;
import com.further.spring.boot.further.Dto.DetalleCompraDTO;
import com.further.spring.boot.further.Entity.Compra;
import com.further.spring.boot.further.Entity.DetalleCompra;
import com.further.spring.boot.further.Entity.Producto;
import com.further.spring.boot.further.Entity.Proveedor;
import com.further.spring.boot.further.Exception.TipoMovimientoKardex;
import com.further.spring.boot.further.Mapper.CompraMapper;
import com.further.spring.boot.further.Repository.CompraRepository;
import com.further.spring.boot.further.Repository.ProductoRepository;
import com.further.spring.boot.further.Repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private KardexService kardexService;

    @Autowired
    private CompraMapper compraMapper;

    public List<CompraDTO> obtenerTodasCompras() {
        return compraMapper.toDTOList(compraRepository.findAll());
    }

    public Optional<Compra> obtenerCompraPorId(Long id) {
        return compraRepository.findById(id);
    }

    public CompraDTO crearCompra(CompraDTO compraDTO) {

        Compra compra = new Compra();

        Proveedor proveedor = proveedorRepository.findById(compraDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        compra.setProveedor(proveedor);

        compra.setTipoComprobante(compraDTO.getTipoComprobante());
        compra.setSerie(compraDTO.getSerie());
        compra.setNumero(compraDTO.getNumero());

        compra.setFechaCompra(new Date());

        compra.setMoneda(compraDTO.getMoneda());

        compra.setEstado("EMITIDA");
        compra.setEstadoPago("PENDIENTE");

        if (compraDTO.getDetalles() == null || compraDTO.getDetalles().isEmpty()) {
            throw new RuntimeException("La compra debe tener al menos un detalle");
        }

        List<DetalleCompra> detalles = new ArrayList<>();

        double subTotal = 0.0;

        for (DetalleCompraDTO dto : compraDTO.getDetalles()) {

            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (dto.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a cero");
            }

            DetalleCompra detalle = new DetalleCompra();

            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());

            double subtotalDetalle =
                    dto.getCantidad() * dto.getPrecioUnitario();

            detalle.setSubtotal(subtotalDetalle);
            
            subTotal += subtotalDetalle;

            Integer stockAnterior = producto.getStock();

            producto.setStock(stockAnterior + dto.getCantidad());

            productoRepository.save(producto);

            kardexService.registrarMovimiento(
                    producto,
                    TipoMovimientoKardex.ENTRADA,
                    "COMPRA",
                    dto.getCantidad(),
                    stockAnterior,
                    producto.getStock(),
                    "Ingreso por compra"
            );

            detalles.add(detalle);
        }

        double igv =
                Math.round((subTotal * 0.18) * 100.0) / 100.0;

        double total =
                Math.round((subTotal + igv) * 100.0) / 100.0;

        compra.setDetalles(detalles);

        compra.setSubtotal(subTotal);
        compra.setIgv(igv);
        compra.setTotalCompra(total);

        Compra compraGuardada = compraRepository.save(compra);

        return compraMapper.toDTO(compraGuardada);
    }

    public void eliminarCompra(Long id) {
        compraRepository.deleteById(id);
    }

    @Transactional
    public CompraDTO registrarPagoCompra(Long compraId) {

        // 1. Buscamos la compra que se quiere pagar
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        // 2. Le cambiamos el estado de pago
        compra.setEstadoPago("PAGADA");
        compra.setEstado("EMITIDA");

        // 3. Guardamos los cambios en la base de datos
        Compra compraActualizada = compraRepository.save(compra);

        // 4. Devolvemos la compra ya actualizada
        return compraMapper.toDTO(compraActualizada);
    }

    public String obtenerProximoNumero(String tipoComprobante) {

        String tipo = tipoComprobante.trim().toUpperCase();

        String serie = "FACTURA".equals(tipo)
                ? "F001"
                : "B001";

        String ultimoNumero =
                compraRepository.obtenerUltimoNumeroPorSerie(serie);

        long correlativo = 1;

        if (ultimoNumero != null && !ultimoNumero.isBlank()) {

            correlativo =
                    Long.parseLong(ultimoNumero) + 1;
        }

        return String.format("%06d", correlativo);
    }
}