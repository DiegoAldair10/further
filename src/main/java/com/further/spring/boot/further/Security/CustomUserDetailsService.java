package com.further.spring.boot.further.Security;

import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Usuarios usuario = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var authorities = usuario.getRoles()
                .stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()))
                .toList();

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }
}