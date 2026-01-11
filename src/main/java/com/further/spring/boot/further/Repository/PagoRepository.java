package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}