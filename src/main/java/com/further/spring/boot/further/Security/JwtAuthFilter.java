package com.further.spring.boot.further.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    auth.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(req)
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            chain.doFilter(req, res);

        } catch (ExpiredJwtException e) {
            writeJsonError(res, 401, "TOKEN_EXPIRED", "La sesión expiró. Inicia sesión nuevamente.");
        } catch (JwtException | IllegalArgumentException e) {
            writeJsonError(res, 401, "TOKEN_INVALID", "Token inválido.");
        } catch (UsernameNotFoundException e) {
            writeJsonError(res, 401, "USER_NOT_FOUND", "Usuario no encontrado.");
        }
    }

    private void writeJsonError(
            HttpServletResponse res,
            int status,
            String error,
            String message
    ) throws IOException {

        res.setStatus(status);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write("""
                {
                  "status": %d,
                  "error": "%s",
                  "message": "%s"
                }
                """.formatted(status, error, message));
    }
}