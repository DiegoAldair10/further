package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Dto.CompraDTO;
import com.further.spring.boot.further.Dto.DetalleCompraDTO;
import com.further.spring.boot.further.Entity.Compra;
import com.further.spring.boot.further.Entity.DetalleCompra;
import com.further.spring.boot.further.Entity.Producto;
import com.further.spring.boot.further.Entity.Proveedor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraMapper {

    /**
     * Entity -> DTO
     */
    public CompraDTO toDTO(Compra compra){

        if(compra == null){
            return null;
        }

        CompraDTO dto = new CompraDTO();

        dto.setCompraId(compra.getCompraId());

        if(compra.getProveedor()!=null){
            dto.setProveedorId(compra.getProveedor().getProveedorId());
            dto.setProveedor(compra.getProveedor().getNombre());
        }

        dto.setFechaCompra(compra.getFechaCompra());
        dto.setTipoComprobante(compra.getTipoComprobante());
        dto.setSerie(compra.getSerie());
        dto.setNumero(compra.getNumero());
        dto.setMoneda(compra.getMoneda());
        dto.setSubtotal(compra.getSubtotal());
        dto.setIgv(compra.getIgv());
        dto.setTotalCompra(compra.getTotalCompra());
        dto.setEstado(compra.getEstado());
        dto.setEstadoPago(compra.getEstadoPago());

        if(compra.getDetalles()!=null){
            dto.setDetalles(
                    compra.getDetalles()
                            .stream()
                            .map(this::toDetalleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    /**
     * DTO -> Entity
     */
    public Compra toEntity(CompraDTO dto){

        if(dto == null){
            return null;
        }

        Compra compra = new Compra();

        compra.setCompraId(dto.getCompraId());

        if(dto.getProveedorId()!=null){
            Proveedor proveedor = new Proveedor();
            proveedor.setProveedorId(dto.getProveedorId());
            compra.setProveedor(proveedor);
        }

        compra.setFechaCompra(dto.getFechaCompra());
        compra.setTipoComprobante(dto.getTipoComprobante());
        compra.setSerie(dto.getSerie());
        compra.setNumero(dto.getNumero());
        compra.setMoneda(dto.getMoneda());
        compra.setSubtotal(dto.getSubtotal());
        compra.setIgv(dto.getIgv());
        compra.setTotalCompra(dto.getTotalCompra());
        compra.setEstado(dto.getEstado());
        compra.setEstadoPago(dto.getEstadoPago());

        if(dto.getDetalles()!=null){

            List<DetalleCompra> detalles = new ArrayList<>();

            for(DetalleCompraDTO detalleDTO : dto.getDetalles()){

                DetalleCompra detalle = toDetalleEntity(detalleDTO);

                detalle.setCompra(compra);

                detalles.add(detalle);

            }

            compra.setDetalles(detalles);

        }

        return compra;

    }

    /**
     * Detalle Entity -> DTO
     */
    public DetalleCompraDTO toDetalleDTO(DetalleCompra detalle){

        if(detalle==null){
            return null;
        }

        DetalleCompraDTO dto = new DetalleCompraDTO();

        dto.setDetalleCompraId(detalle.getDetalleCompraId());

        if(detalle.getProducto()!=null){
            dto.setProductoId(detalle.getProducto().getProductoId());
            dto.setProducto(detalle.getProducto().getNombre());
        }

        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());

        return dto;

    }

    /**
     * Detalle DTO -> Entity
     */
    public DetalleCompra toDetalleEntity(DetalleCompraDTO dto){

        if(dto==null){
            return null;
        }

        DetalleCompra detalle = new DetalleCompra();

        detalle.setDetalleCompraId(dto.getDetalleCompraId());

        if(dto.getProductoId()!=null){

            Producto producto = new Producto();
            producto.setProductoId(dto.getProductoId());

            detalle.setProducto(producto);

        }

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());

        return detalle;

    }

    /**
     * Lista Entity -> DTO
     */
    public List<CompraDTO> toDTOList(List<Compra> compras){

        return compras.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }

}