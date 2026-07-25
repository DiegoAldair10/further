package com.further.spring.boot.further.Controller;
import com.further.spring.boot.further.Dto.AuthResponse;
import com.further.spring.boot.further.Dto.LoginRequestDTO;
import com.further.spring.boot.further.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63842")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequestDTO req) {
        return service.login(req);
    }
}