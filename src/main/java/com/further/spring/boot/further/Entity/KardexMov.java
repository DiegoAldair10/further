package com.further.spring.boot.further.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "KARDEX_MOV")
public class KardexMov {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_kardex")
    @SequenceGenerator(
            name = "seq_kardex",
            sequenceName = "SEQ_KARDEX",
            allocationSize = 1)
    @Column(name = "MOVID")
    private Long movId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTOID")
    private Producto producto;

    @Column(name = "FECHA_MOV")
    private LocalDateTime fechaMov;

    @Column(name = "TIPO_MOV")
    private String tipoMov;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "VENTADETALLEID")
    private Long ventaDetalleId;

    @Column(name = "COMPRADETALLEID")
    private Long compraDetalleId;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "STOCK_ANTERIOR")
    private Integer stockAnterior;

    @Column(name = "STOCK_NUEVO")
    private Integer stockNuevo;

    @Column(name = "OBSERVACION")
    private String observacion;
}