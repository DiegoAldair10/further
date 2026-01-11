package com.further.spring.boot.further.Entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "PAGOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pagoId")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAGOS")
    @SequenceGenerator(name = "SEQ_PAGOS", sequenceName = "SEQ_PAGOS", allocationSize = 1)
    @Column(name = "PAGOID")
    private Long pagoId;

    @ManyToOne
    @JoinColumn(name = "VENTAID", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "METODOPAGOID", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "MONTO", nullable = false)
    private Double monto;

    @Column(name = "FECHA_PAGO")
    private Date fecha_Pago = new Date();
}