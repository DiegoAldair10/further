package com.further.spring.boot.further.Security;


import com.further.spring.boot.further.Entity.Usuarios;
import com.further.spring.boot.further.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol())  // "ADMIN", "USER"
                .disabled(user.getEstado() == 0)
                .build();
    }
}