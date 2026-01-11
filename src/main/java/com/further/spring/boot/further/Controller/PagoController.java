package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.PagoDTO;
import com.further.spring.boot.further.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodosPagos() {
        List<PagoDTO> pagos = pagoService.obtenerTodosPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.obtenerPagoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagoDTO> crearPago(@RequestBody PagoDTO pagoDTO) {
        PagoDTO nuevoPago = pagoService.crearPago(pagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
