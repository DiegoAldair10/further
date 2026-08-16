package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.*;
import com.further.spring.boot.further.Mapper.KardexSpecification;
import com.further.spring.boot.further.Repository.KardexRepository;
import com.further.spring.boot.further.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private KardexRepository kardexRepository;

    public InventarioDashboardDTO obtenerDashboard() {

        InventarioDashboardDTO dto = new InventarioDashboardDTO();

        dto.setStockActual(
                productoRepository.obtenerStockTotal().doubleValue());

        dto.setEntradasHoy(
                kardexRepository.obtenerEntradasHoy());

        dto.setSalidasHoy(
                kardexRepository.obtenerSalidasHoy());

        dto.setProductosStockBajo(
                productoRepository.obtenerProductosStockBajo());

        return dto;
    }

    public List<MovimientoMesDTO> obtenerMovimientosMes() {

        List<Object[]> lista = kardexRepository.obtenerMovimientosMes();

        return lista.stream()
                .map(x -> new MovimientoMesDTO(
                        x[0].toString(),
                        ((Number) x[1]).intValue(),
                        ((Number) x[2]).intValue()))
                .toList();

    }

    public List<ProductoStockDTO> obtenerProductosStockBajo() {

        return productoRepository
                .obtenerProductosStockBajos()
                .stream()
                .map(p -> new ProductoStockDTO(
                        p.getProductoId(),
                        p.getNombre(),
                        p.getStock()))
                .toList();

    }

    public List<KardexDTO> obtenerUltimosMovimientos() {

        return kardexRepository
                .findTop10ByOrderByFechaMovDesc()
                .stream()
                .map(k -> new KardexDTO(
                        k.getMovId(),
                        k.getProducto().getProductoId(),
                        k.getProducto().getNombre(),
                        k.getFechaMov(),
                        k.getTipoMov(),
                        k.getOrigen(),
                        k.getCantidad(),
                        k.getStockAnterior(),
                        k.getStockNuevo(),
                        k.getObservacion()
                )).toList();

    }

    public List<StockCategoriaDTO> obtenerStockCategoria() {

        List<Object[]> resultado =
                productoRepository.obtenerStockCategoria();

        return resultado.stream()
                .map(x -> new StockCategoriaDTO(

                        x[0].toString(),

                        ((Number) x[1]).intValue()

                ))
                .toList();

    }

    public List<KardexDTO> obtenerKardex(
            Long productoId,
            String tipoMov,
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        return kardexRepository
                .findAll(
                        KardexSpecification.filtrar(
                                productoId,
                                tipoMov,
                                fechaInicio,
                                fechaFin))
                .stream()
                .map(k -> new KardexDTO(
                        k.getMovId(),
                        k.getProducto().getProductoId(),
                        k.getProducto().getNombre(),
                        k.getFechaMov(),
                        k.getTipoMov(),
                        k.getOrigen(),
                        k.getCantidad(),
                        k.getStockAnterior(),
                        k.getStockNuevo(),
                        k.getObservacion()))
                .toList();
    }



    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(p -> new ProductoDTO(
                        p.getProductoId(),
                        p.getNombre()))
                .toList();
    }

}