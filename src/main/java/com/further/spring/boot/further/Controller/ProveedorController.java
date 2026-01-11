package com.further.spring.boot.further.Controller;


import com.further.spring.boot.further.Dto.ProveedorDTO;
import com.further.spring.boot.further.Entity.Proveedor;
import com.further.spring.boot.further.Service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public   ResponseEntity <List<ProveedorDTO>> obtenerTodosProveedores() {
        List<ProveedorDTO> proveedores = proveedorService.obtenerTodosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerProveedorPorId(@PathVariable Long id) {
        return proveedorService.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public   ResponseEntity <ProveedorDTO> crearProveedor( @Valid @RequestBody ProveedorDTO proveedorDTO) {
        ProveedorDTO nuevoProveedor = proveedorService.crearProveedor(proveedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDTO proveedorDTO) {
       ProveedorDTO proveedorActualizado = proveedorService.actualizarProveedor(id, proveedorDTO);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
