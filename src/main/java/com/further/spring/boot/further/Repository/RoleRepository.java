package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByNombreIgnoreCase(String nombre);

}