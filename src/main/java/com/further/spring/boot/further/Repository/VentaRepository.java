package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT v FROM Venta v JOIN FETCH v.cliente JOIN FETCH v.empleado WHERE v.ventaId = :id")
    Venta findByIdWithClienteAndEmpleado(@Param("id") Long id);

    @Query("SELECT v FROM Venta v " +
            "LEFT JOIN FETCH v.detalles d " +
            "LEFT JOIN FETCH d.producto " +
            "WHERE v.ventaId = :id")
    Optional<Venta> findByIdWithDetallesAndProductos(@Param("id") Long id);

    @Query("SELECT v FROM Venta v " +
            "JOIN FETCH v.cliente " +
            "JOIN FETCH v.empleado " +
            "LEFT JOIN FETCH v.detalles d " +
            "LEFT JOIN FETCH d.producto " +
            "WHERE v.ventaId = :id")
    Optional<Venta> findByIdWithAllRelations(@Param("id") Long id);

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.numeroComprobante LIKE CONCAT(:serie, '%')")
    Long countBySerie(@Param("serie") String serie);
}
