package com.further.spring.boot.further.Repository;


import com.further.spring.boot.further.Entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.productoId = :productoId AND p.stock >= :cantidad")
    int disminuirStock(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    @Query("SELECT COALESCE(SUM(p.stock),0) FROM Producto p")
    Integer obtenerStockTotal();

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.stock <= 5")
    Long obtenerProductosStockBajo();

    @Query("""
            SELECT p
            FROM Producto p
            WHERE p.stock<=5
            ORDER BY p.stock ASC
            """)
    List<Producto> obtenerProductosStockBajos();

    @Query("""
            SELECT
            p.categoria.nombre,
            COALESCE(SUM(p.stock),0)
            
            FROM Producto p
            
            GROUP BY p.categoria.nombre
            
            ORDER BY p.categoria.nombre
            """)
    List<Object[]> obtenerStockCategoria();
}