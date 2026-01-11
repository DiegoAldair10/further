package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
