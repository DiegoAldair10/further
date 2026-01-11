package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.VentaDTO;
import com.further.spring.boot.further.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:4200")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        List<VentaDTO> ventas = ventaService.obtenerTodasVentas();
        return ResponseEntity.ok(ventas);
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva venta
    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO nuevaVenta = ventaService.crearVenta(ventaDTO); // ✅ debe coincidir con el método correcto
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (Exception e) {
            e.printStackTrace(); // 👈 verás el error exacto en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar venta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/proximo-numero/{tipo}")
    public ResponseEntity<String> obtenerProximoNumero(@PathVariable String tipo) {
        try {
            String numero = ventaService.obtenerProximoNumero(tipo);
            return ResponseEntity.ok(numero); // devuelve texto plano, ej: F001-000123
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR");
        }
    }

    // Actualizar venta por ID
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO ventaActualizada = ventaService.actualizarVenta(id, ventaDTO);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
