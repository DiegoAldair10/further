package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.*;
import com.further.spring.boot.further.Service.InventarioService;
import com.further.spring.boot.further.Service.JasperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private JasperService jasperService;

    @GetMapping("/dashboard")
    public InventarioDashboardDTO dashboard() {

        return inventarioService.obtenerDashboard();

    }

    @GetMapping("/movimientos-mes")
    public List<MovimientoMesDTO> movimientosMes() {

        return inventarioService.obtenerMovimientosMes();

    }

    @GetMapping("/productos-stock-bajo")
    public List<ProductoStockDTO> productosStockBajo() {

        return inventarioService.obtenerProductosStockBajo();

    }

    @GetMapping("/ultimos-movimientos")
    public List<KardexDTO> ultimosMovimientos() {

        return inventarioService.obtenerUltimosMovimientos();

    }

    @GetMapping("/stock-categoria")
    public List<StockCategoriaDTO> stockCategoria(){

        return inventarioService.obtenerStockCategoria();

    }


    @GetMapping("/productos")
    public List<ProductoDTO> listarProductos() {
        return inventarioService.listarProductos();
    }

    @GetMapping("/kardex")
    public ResponseEntity<List<KardexDTO>> obtenerKardex(

            @RequestParam(required = false) Long productoId,

            @RequestParam(required = false) String tipoMov,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) {

        return ResponseEntity.ok(
                inventarioService.obtenerKardex(
                        productoId,
                        tipoMov,
                        fechaInicio,
                        fechaFin));
    }

    @GetMapping("/kardex/pdf")
    public ResponseEntity<byte[]> exportarPdf(
            @RequestParam(required = false) Long productoId,
            @RequestParam(required = false) String tipoMov,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin) throws Exception {

        List<KardexDTO> lista =
                inventarioService.obtenerKardex(
                        productoId,
                        tipoMov,
                        fechaInicio,
                        fechaFin);

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("titulo", "REPORTE DE KARDEX");

        byte[] pdf = jasperService.generarReporte(
                "KardexReport",
                parametros,
                lista);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=kardex.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}