package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Entity.Compra;
import com.further.spring.boot.further.Service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<Compra> obtenerTodasCompras() {
        return compraService.obtenerTodasCompras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Long id) {
        return compraService.obtenerCompraPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return compraService.crearCompra(compra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Long id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }
}