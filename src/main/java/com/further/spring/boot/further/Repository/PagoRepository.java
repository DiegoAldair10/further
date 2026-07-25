package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("""
            SELECT COUNT(p)
            FROM Pago p
            WHERE p.venta.ventaId = :ventaId
            AND p.estado = 'PAGADO'
            """)
    Long contarPagosActivosPorVenta(
            @Param("ventaId") Long ventaId
    );

    void deleteByVentaVentaId(Long ventaId);

    List<Pago> findByUsuarioUsuariosId(Long usuariosId);
}