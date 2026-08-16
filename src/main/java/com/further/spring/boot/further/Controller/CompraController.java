package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.CompraDTO;
import com.further.spring.boot.further.Entity.Compra;
import com.further.spring.boot.further.Service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63842")
@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<CompraDTO> obtenerTodasCompras() {
        return compraService.obtenerTodasCompras();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Long id) {
        return compraService.obtenerCompraPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompraDTO> crearCompra(
            @RequestBody CompraDTO compraDTO){

        return ResponseEntity.ok(
                compraService.crearCompra(compraDTO));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Long id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/proximo-numero/{tipoComprobante}")
    public ResponseEntity<String> getProximoNumero(@PathVariable String tipoComprobante) {
        String proximoNumero = compraService.obtenerProximoNumero(tipoComprobante);
        return ResponseEntity.ok(proximoNumero);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<CompraDTO> pagarCompra(@PathVariable Long id) {
        CompraDTO compraPagada = compraService.registrarPagoCompra(id);
        return ResponseEntity.ok(compraPagada);
    }
}