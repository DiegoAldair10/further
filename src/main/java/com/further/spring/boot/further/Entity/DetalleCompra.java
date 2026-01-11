package com.further.spring.boot.further.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DETALLECOMPRAS")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLECOMPRAS")
    @SequenceGenerator(name = "SEQ_DETALLECOMPRAS", sequenceName = "SEQ_DETALLECOMPRAS", allocationSize = 1)
    @Column(name = "DETALLECOMPRAID")
    private Long detalleCompraId;

    @ManyToOne
    @JoinColumn(name = "COMPRAID", nullable = false)
    @JsonBackReference
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "PRODUCTOID", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "PRECIOUNITARIO")
    private Double precioUnitario;

    @Column(name = "SUBTOTAL", insertable = false, updatable = false)
    private Double subtotal;
}