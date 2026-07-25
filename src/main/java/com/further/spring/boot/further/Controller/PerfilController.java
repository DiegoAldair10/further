package com.further.spring.boot.further.Controller;

import com.further.spring.boot.further.Dto.ActualizarPerfilDTO;
import com.further.spring.boot.further.Dto.CambiarPasswordDTO;
import com.further.spring.boot.further.Dto.PerfilDTO;
import com.further.spring.boot.further.Service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63842")
@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public PerfilDTO obtenerPerfil(
            Authentication authentication
    ) {
        return perfilService.obtenerPerfil(authentication);
    }

    @PutMapping
    public PerfilDTO actualizarPerfil(
            Authentication authentication,
            @RequestBody ActualizarPerfilDTO dto
    ) {
        return perfilService.actualizarPerfil(
                authentication,
                dto
        );
    }

    @PutMapping("/password")
    public void cambiarPassword(
            Authentication authentication,
            @RequestBody CambiarPasswordDTO dto
    ) {
        perfilService.cambiarPassword(
                authentication,
                dto
        );
    }
}