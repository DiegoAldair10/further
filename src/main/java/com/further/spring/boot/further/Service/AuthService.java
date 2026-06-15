package com.further.spring.boot.further.Service;

import com.further.spring.boot.further.Dto.AuthResponse;
import com.further.spring.boot.further.Dto.LoginRequestDTO;
import com.further.spring.boot.further.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequestDTO request) {
        try {
            var auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails user = (UserDetails) auth.getPrincipal();

            String token = jwtService.generateToken(user);

            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return new AuthResponse(token, user.getUsername(), roles);

        } catch (DisabledException e) {
            throw new RuntimeException("Usuario inactivo");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }
}