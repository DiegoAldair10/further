package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("""
                SELECT MAX(v.numeroComprobante)
                FROM Venta v
                WHERE v.serie = :serie
            """)
    String obtenerUltimoNumeroPorSerie(@Param("serie") String serie);


    @Query("""
            SELECT COALESCE(SUM(v.total),0)
            FROM Venta v
            """)
    Double obtenerMontoTotalVentas();

    @Query("""
            SELECT COALESCE(SUM(v.total),0)
            FROM Venta v
            WHERE v.usuario.usuariosId = :usuarioId
            """)
    Double obtenerMontoTotalVentasPorUsuario(
            @Param("usuarioId") Long usuarioId
    );

    Long countByUsuarioUsuariosId(
            Long usuarioId
    );

    List<Venta> findByUsuarioUsuariosId(Long usuariosId);


    @Query(value = """
            SELECT
                TO_CHAR(FECHAVENTA,'MON','NLS_DATE_LANGUAGE=SPANISH') AS mes,
                SUM(TOTAL) AS total
            FROM VENTAS
            GROUP BY TO_CHAR(FECHAVENTA,'MON','NLS_DATE_LANGUAGE=SPANISH'),
                     EXTRACT(MONTH FROM FECHAVENTA)
            ORDER BY EXTRACT(MONTH FROM FECHAVENTA)
            """, nativeQuery = true)
    List<Object[]> obtenerVentasPorMes();
}
