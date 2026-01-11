package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.ClienteDTO;
import com.further.spring.boot.further.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity <List<ClienteDTO>> ListarClientes() {
        List<ClienteDTO> clientes = clienteService.obtenerTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
