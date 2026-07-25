package com.further.spring.boot.further.Service;


import com.further.spring.boot.further.Dto.ActualizarPerfilDTO;
import com.further.spring.boot.further.Dto.CambiarPasswordDTO;
import com.further.spring.boot.further.Dto.PerfilDTO;
import com.further.spring.boot.further.Entity.Empleado;
import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Repository.EmpleadoRepository;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PerfilDTO obtenerPerfil(Authentication authentication) {

        String email = authentication.getName();

        Usuarios usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return convertirADTO(usuario);
    }

    @Transactional
    public PerfilDTO actualizarPerfil(
            Authentication authentication,
            ActualizarPerfilDTO dto
    ) {

        String email = authentication.getName();

        Usuarios usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Empleado empleado = usuario.getEmpleado();

        if (empleado == null) {
            throw new RuntimeException(
                    "El usuario no tiene un empleado asociado"
            );
        }

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setTelefono(dto.getTelefono());
        empleado.setEmail(dto.getEmail());

        empleadoRepository.save(empleado);

        usuario.setEmail(dto.getEmail());

        usuarioRepository.save(usuario);

        return convertirADTO(usuario);
    }

    @Transactional
    public void cambiarPassword(
            Authentication authentication,
            CambiarPasswordDTO dto
    ) {

        String email = authentication.getName();

        Usuarios usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(
                dto.getPasswordActual(),
                usuario.getPassword()
        )) {
            throw new RuntimeException(
                    "La contraseña actual es incorrecta"
            );
        }

        if (!dto.getNuevaPassword()
                .equals(dto.getConfirmarPassword())) {

            throw new RuntimeException(
                    "Las contraseñas no coinciden"
            );
        }

        usuario.setPassword(
                passwordEncoder.encode(dto.getNuevaPassword())
        );

        usuarioRepository.save(usuario);
    }

    private PerfilDTO convertirADTO(Usuarios usuario) {

        PerfilDTO dto = new PerfilDTO();

        dto.setUsuarioId(usuario.getUsuariosId());
        dto.setEmail(usuario.getEmail());
        dto.setEstado(usuario.getEstado());

        dto.setRoles(
                usuario.getRoles()
                        .stream()
                        .map(rol -> rol.getNombre())
                        .collect(Collectors.toList())
        );

        if (usuario.getEmpleado() != null) {

            Empleado empleado = usuario.getEmpleado();

            dto.setEmpleadoId(empleado.getEmpleadoId());
            dto.setNombre(empleado.getNombre());
            dto.setApellido(empleado.getApellido());
            dto.setTelefono(empleado.getTelefono());
            dto.setCargo(empleado.getCargo());
        }

        return dto;
    }
}