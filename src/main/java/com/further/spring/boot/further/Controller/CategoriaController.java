package com.further.spring.boot.further.Controller;


import com.further.spring.boot.further.Dto.CategoriaDTO;
import com.further.spring.boot.further.Dto.MetodoPagoDTO;
import com.further.spring.boot.further.Service.CategoriaService;
import com.further.spring.boot.further.Service.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> ListarCategoria() {
        List<CategoriaDTO> categoria = categoriaService.obtenerCategoria();
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuenvoCategoria = categoriaService.crearCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuenvoCategoria);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaActualizar = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizar);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}