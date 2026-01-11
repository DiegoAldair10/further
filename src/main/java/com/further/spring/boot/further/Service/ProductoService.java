package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.ProductoDTO;
import com.further.spring.boot.further.Entity.Categoria;
import com.further.spring.boot.further.Entity.Producto;
import com.further.spring.boot.further.Mapper.ProductoMapper;
import com.further.spring.boot.further.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> obtenerTodosProductos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO);
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDTO(productoGuardado);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDTO.getNombre());
                    producto.setDescripcion(productoDTO.getDescripcion());
                    producto.setPrecio_venta(productoDTO.getPrecio_venta());
                    producto.setCosto_promedio(productoDTO.getCosto_promedio());

                    Categoria categoria = new Categoria();
                    categoria.setCategoriaId(productoDTO.getCategoriaId());
                    producto.setCategoria(categoria);

                    producto.setStock(productoDTO.getStock());
                    producto.setEstado(productoDTO.getEstado());
                    return productoRepository.save(producto);
                })
                .map(productoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}