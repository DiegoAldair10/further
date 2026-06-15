package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.UserRequestDTO;
import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public Usuarios create(@RequestBody UserRequestDTO req) {
        return service.create(req);
    }

    @GetMapping
    public List<Usuarios> list() {
        return service.list();
    }

    @PutMapping("/{id}")
    public Usuarios update(@PathVariable Long id, @RequestBody UserRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                "Usuario eliminado correctamente"
        );
    }
}