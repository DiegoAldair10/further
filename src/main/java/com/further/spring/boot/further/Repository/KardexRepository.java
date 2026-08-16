package com.further.spring.boot.further.Repository;

import com.further.spring.boot.further.Entity.KardexMov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KardexRepository extends
        JpaRepository<KardexMov, Long>,
        JpaSpecificationExecutor<KardexMov> {

    @Query(value = """
            SELECT NVL(SUM(CANTIDAD),0)
            FROM KARDEX_MOV
            WHERE TIPO_MOV='IN'
            AND TRUNC(FECHA_MOV)=TRUNC(SYSDATE)
            """, nativeQuery = true)
    Double obtenerEntradasHoy();

    @Query(value = """
            SELECT NVL(SUM(CANTIDAD),0)
            FROM KARDEX_MOV
            WHERE TIPO_MOV='OUT'
            AND TRUNC(FECHA_MOV)=TRUNC(SYSDATE)
            """, nativeQuery = true)
    Double obtenerSalidasHoy();

    @Query(value = """
            SELECT
                TO_CHAR(FECHA_MOV,'Mon') MES,
                SUM(CASE WHEN TIPO_MOV='IN' THEN CANTIDAD ELSE 0 END) ENTRADAS,
                SUM(CASE WHEN TIPO_MOV='OUT' THEN CANTIDAD ELSE 0 END) SALIDAS
            FROM KARDEX_MOV
            GROUP BY
                TO_CHAR(FECHA_MOV,'Mon'),
                EXTRACT(MONTH FROM FECHA_MOV)
            ORDER BY
                EXTRACT(MONTH FROM FECHA_MOV)
            """, nativeQuery = true)
    List<Object[]> obtenerMovimientosMes();

    @Query("""
                SELECT k
                FROM KardexMov k
                ORDER BY k.fechaMov DESC
            """)
    List<KardexMov> findTop10ByOrderByFechaMovDesc();


}