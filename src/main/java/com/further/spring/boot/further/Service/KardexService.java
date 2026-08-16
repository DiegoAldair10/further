package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Entity.KardexMov;
import com.further.spring.boot.further.Entity.Producto;
import com.further.spring.boot.further.Repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    public void registrarMovimiento(
            Producto producto,
            String tipoMovimiento,
            String origen,
            Integer cantidad,
            Integer stockAnterior,
            Integer stockNuevo,
            String observacion){

        KardexMov kardex = new KardexMov();

        kardex.setProducto(producto);
        kardex.setFechaMov(LocalDateTime.now());
        kardex.setTipoMov(tipoMovimiento);
        kardex.setOrigen(origen);
        kardex.setCantidad(cantidad);
        kardex.setStockAnterior(stockAnterior);
        kardex.setStockNuevo(stockNuevo);
        kardex.setObservacion(observacion);

        kardexRepository.save(kardex);

    }

}