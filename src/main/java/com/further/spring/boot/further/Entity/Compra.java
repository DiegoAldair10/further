package com.further.spring.boot.further.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "COMPRAS")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compras")
    @SequenceGenerator(name = "seq_compras", sequenceName = "Seq_Compras", allocationSize = 1)
    @Column(name = "COMPRAID")
    private Long compraId;

    @ManyToOne
    @JoinColumn(name = "PROVEEDORID", nullable = false)
    private Proveedor proveedor;

    @Column(name = "FECHACOMPRA")
    private Date fechaCompra = new Date();

    @Column(name = "TOTALCOMPRA")
    private Double totalCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetalleCompra> detalles;
}