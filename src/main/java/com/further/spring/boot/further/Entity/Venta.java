package com.further.spring.boot.further.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "VENTAS")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ventas")
    @SequenceGenerator(name = "seq_ventas", sequenceName = "Seq_Ventas", allocationSize = 1)
    @Column(name = "VENTAID")
    private Long ventaId;

    @ManyToOne
    @JoinColumn(name = "CLIENTEID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "EMPLEADOID", nullable = false)
    private Empleado empleado;

    @Column(name = "FECHAVENTA")
    private Date fechaVenta = new Date();

    @Column(name = "TIPOCOMPROBANTE")
    private String tipoComprobante;

    @Column(name = "SERIE")
    private String serie;

    @Column(name = "NUMEROCOMPROBANTE")
    private String numeroComprobante;

    @Column(name = "MONEDA")
    private String moneda;

    @Column(name = "SUBTOTAL")
    private Double subTotal;

    @Column(name = "IGV")
    private Double igv;

    @Column(name = "TOTAL")
    private Double total;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ESTADO_PAGO")
    private String estadoPago;

    @Column(name = "FECHA_CREACION")
    private Date fecha_Creacion = new Date();


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles;


}
