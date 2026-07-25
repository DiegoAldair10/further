package com.further.spring.boot.further.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Temporal para crear primer usuario. Después cámbialo a ADMIN.
                        // .requestMatchers(HttpMethod.POST, "/users").permitAll()
// Dashboard
                                .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "USER")

                                // Productos
                                .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")

                                // Categorías
                                .requestMatchers(HttpMethod.GET, "/api/categoria/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/categoria/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/categoria/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/categoria/**").hasRole("ADMIN")

                                // Clientes
                                .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/clientes/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")

                                // Ventas
                                .requestMatchers(HttpMethod.GET, "/api/ventas/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/ventas/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/ventas/**").hasAnyRole("ADMIN","USER")

                                // Pagos
                                .requestMatchers(HttpMethod.GET, "/api/pago/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/pago/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/pago/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/pago/**").hasRole("ADMIN")

                                // Empleados
                                .requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/empleados/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/empleados/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/empleados/**").hasRole("ADMIN")
                                // Proveedores
                                .requestMatchers("/api/proveedores/**").hasRole("ADMIN")

                                // Métodos de pago
                                .requestMatchers("/api/metodos-pago/**").hasRole("ADMIN")

                                // Usuarios
                                .requestMatchers("/users/**").hasRole("ADMIN")

                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        ObjectMapper mapper = new ObjectMapper();

        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 401);
            body.put("error", "UNAUTHORIZED");
            body.put("message", "No autenticado. Inicia sesión para continuar.");
            body.put("path", request.getRequestURI());

            mapper.writeValue(response.getWriter(), body);
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        ObjectMapper mapper = new ObjectMapper();

        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 403);
            body.put("error", "FORBIDDEN");
            body.put("message", "Acceso denegado: no tienes permisos para este recurso.");
            body.put("path", request.getRequestURI());

            mapper.writeValue(response.getWriter(), body);
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:63842"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}