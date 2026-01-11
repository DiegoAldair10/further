package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
}
