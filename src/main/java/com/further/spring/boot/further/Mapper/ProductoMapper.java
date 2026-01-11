package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.ProductoDTO;
import com.further.spring.boot.further.Entity.Categoria;
import com.further.spring.boot.further.Entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setProductoId(producto.getProductoId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio_venta(producto.getPrecio_venta());
        dto.setCosto_promedio(producto.getCosto_promedio());
        dto.setCategoriaId(producto.getCategoria().getCategoriaId());
        dto.setStock(producto.getStock());
        dto.setEstado(producto.getEstado());
        dto.setFecha_Creacion(producto.getFecha_Creacion());
        return dto;
    }


    public Producto toEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setProductoId(dto.getProductoId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio_venta(dto.getPrecio_venta());
        producto.setCosto_promedio(dto.getCosto_promedio());
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(dto.getCategoriaId());
        producto.setCategoria(categoria);
        producto.setStock(dto.getStock());
        producto.setEstado(dto.getEstado());
        producto.setFecha_Creacion(dto.getFecha_Creacion());
        return producto;
    }
}
