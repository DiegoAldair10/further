package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

}
