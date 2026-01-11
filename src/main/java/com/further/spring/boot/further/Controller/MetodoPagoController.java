package com.further.spring.boot.further.Controller;


import com.further.spring.boot.further.Dto.MetodoPagoDTO;
import com.further.spring.boot.further.Service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagoDTO>> ListrPagos() {
        List<MetodoPagoDTO> metodosPago = metodoPagoService.obtenerTodosMetodosPago();
        return ResponseEntity.ok(metodosPago);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> obtenerMetodoPagoPorId(@PathVariable Long id) {
        return metodoPagoService.obtenerMetodoPagoPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodoPagoDTO> crearMetodoPago(@Valid @RequestBody MetodoPagoDTO metodoPagoDTO) {
        MetodoPagoDTO nuevoMetodoPago = metodoPagoService.crearMetodoPago(metodoPagoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMetodoPago);


    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoDTO> actualizarMetodoPago(@PathVariable Long id, @RequestBody MetodoPagoDTO metodoPagoDTO) {

        MetodoPagoDTO metodoPagoActualizado = metodoPagoService.actualizarMetodoPago(id, metodoPagoDTO);
        return ResponseEntity.ok(metodoPagoActualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMetodoPago(@PathVariable Long id) {
        metodoPagoService.eliminarMetodoPago(id);
        return ResponseEntity.noContent().build();
    }
}