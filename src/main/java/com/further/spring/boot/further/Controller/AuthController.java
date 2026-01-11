package com.further.spring.boot.further.Controller;


import com.further.spring.boot.further.Dto.LoginRequestDTO;
import com.further.spring.boot.further.Dto.LoginResponseDTO;
import com.further.spring.boot.further.Security.CustomUserDetailsService;
import com.further.spring.boot.further.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generarToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

