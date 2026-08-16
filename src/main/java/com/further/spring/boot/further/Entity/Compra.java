package com.further.spring.boot.further.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "COMPRAS")
@Data
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compras")
    @SequenceGenerator(name = "seq_compras", sequenceName = "Seq_Compras", allocationSize = 1)
    @Column(name = "COMPRAID")
    private Long compraId;

    @ManyToOne
    @JoinColumn(name = "PROVEEDORID", nullable = false)
    private Proveedor proveedor;

    @Column(name = "FECHA_COMPRA")
    private Date fechaCompra = new Date();

    @Column(name = "TIPO_COMPROBANTE")
    private String tipoComprobante;

    @Column(name = "SERIE")
    private String serie;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "MONEDA")
    private String moneda = "PEN";

    @Column(name = "SUBTOTAL")
    private Double subtotal;

    @Column(name = "IGV")
    private Double igv;

    @Column(name = "TOTAL")
    private Double totalCompra;

    @Column(name = "ESTADO")
    private String estado = "BORRADOR";

    @Column(name = "ESTADO_PAGO")
    private String estadoPago = "PENDIENTE";

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion = new Date();

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleCompra> detalles;
}