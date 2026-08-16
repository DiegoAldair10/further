package com.further.spring.boot.further.Mapper;

import com.further.spring.boot.further.Entity.KardexMov;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KardexSpecification {

    public static Specification<KardexMov> filtrar(
            Long productoId,
            String tipoMov,
            LocalDate fechaInicio,
            LocalDate fechaFin){

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (productoId != null) {
                predicates.add(
                        cb.equal(
                                root.get("producto").get("productoId"),
                                productoId));
            }

            if (tipoMov != null && !tipoMov.isBlank()) {
                predicates.add(
                        cb.equal(
                                root.get("tipoMov"),
                                tipoMov));
            }

            if (fechaInicio != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("fechaMov"),
                                fechaInicio.atStartOfDay()));
            }

            if (fechaFin != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("fechaMov"),
                                fechaFin.atTime(23,59,59)));
            }

            query.orderBy(
                    cb.desc(root.get("fechaMov")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}