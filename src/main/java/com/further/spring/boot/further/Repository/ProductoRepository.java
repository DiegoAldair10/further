package com.further.spring.boot.further.Repository;


import com.further.spring.boot.further.Entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.productoId = :productoId AND p.stock >= :cantidad")
    int disminuirStock(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);
}