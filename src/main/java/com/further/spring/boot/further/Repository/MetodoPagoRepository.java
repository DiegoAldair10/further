package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
}