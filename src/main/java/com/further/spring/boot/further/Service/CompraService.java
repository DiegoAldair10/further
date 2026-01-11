package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Entity.Compra;
import com.further.spring.boot.further.Entity.DetalleCompra;
import com.further.spring.boot.further.Repository.CompraRepository;
import com.further.spring.boot.further.Repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ProductoRepository productoRepository; // Necesario para buscar productos


    public List<Compra> obtenerTodasCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> obtenerCompraPorId(Long id) {
        Optional<Compra> compra = compraRepository.findById(id);
        return compra;
    }

    public Compra crearCompra(Compra compra) {
        // Calcular el total de la compra
        double total = compra.getDetalles().stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
        compra.setTotalCompra(total);

        // Guardar la compra
        Compra nuevaCompra = compraRepository.save(compra);

        // Disminuir el stock
        for (DetalleCompra detalle : compra.getDetalles()) {
            int updatedRows = productoRepository.disminuirStock(detalle.getProducto().getProductoId(), detalle.getCantidad());
            if (updatedRows == 0) {
                throw new RuntimeException("Stock insuficiente para el producto: " + detalle.getProducto().getProductoId());
            }
        }

        return nuevaCompra;
    }

    public void eliminarCompra(Long id) {
        compraRepository.deleteById(id);
    }
}