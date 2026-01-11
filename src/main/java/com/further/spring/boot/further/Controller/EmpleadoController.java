package com.further.spring.boot.further.Controller;


import com.further.spring.boot.further.Dto.EmpleadoDTO;
import com.further.spring.boot.further.Entity.Empleado;
import com.further.spring.boot.further.Service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listaEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodosEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO>  crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO nuevoEmpleado = empleadoService.crearEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleadoActualizado = empleadoService.actualizarEmpleado(id, empleadoDTO);
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, empleadoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
