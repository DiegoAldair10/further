package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByEmail(String email);
}