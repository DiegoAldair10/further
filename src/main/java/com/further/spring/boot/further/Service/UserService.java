package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.UserRequestDTO;
import com.further.spring.boot.further.Entity.Roles;
import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Exception.BusinessException;
import com.further.spring.boot.further.Exception.ResourceNotFoundException;
import com.further.spring.boot.further.Repository.RoleRepository;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsuarioRepository repo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public Usuarios create(UserRequestDTO req) {

        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new BusinessException(
                    "Ya existe un usuario registrado con el correo: "
                            + req.getEmail()
            );
        }

        Usuarios user = new Usuarios();
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setEstado(req.getEstado());
        user.setRoles(getRoles(req.getRoles()));

        return repo.save(user);
    }

    public List<Usuarios> list() {
        return repo.findAll();
    }

    public Usuarios update(Long id, UserRequestDTO req) {

        Usuarios user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con ID: " + id
                ));

        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            user.setEmail(req.getEmail());
        }

        if (req.getEstado() != null) {
            user.setEstado(req.getEstado());
        }

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(encoder.encode(req.getPassword()));
        }

        if (req.getRoles() != null && !req.getRoles().isEmpty()) {
            user.setRoles(getRoles(req.getRoles()));
        }

        return repo.save(user);
    }

    public void delete(Long id) {

        Usuarios user = repo.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException(
                "No existe el usuario con ID: " + id
        ));

        repo.delete(user);
    }

    private Set<Roles> getRoles(Set<String> roles) {

        if (roles == null || roles.isEmpty()) {
            throw new RuntimeException("Debe enviar al menos un rol");
        }

        return roles.stream()
                .map(r -> roleRepo.findByNombreIgnoreCase(r.trim())
                        .orElseThrow(() -> new RuntimeException("Rol no existe: " + r)))
                .collect(Collectors.toSet());
    }
}