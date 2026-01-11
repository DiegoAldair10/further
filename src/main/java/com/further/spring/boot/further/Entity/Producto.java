package com.further.spring.boot.further.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTOS")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_productos")
    @SequenceGenerator(name = "seq_productos", sequenceName = "Seq_Productos", allocationSize = 1)
    @Column(name = "PRODUCTOID")
    private Long productoId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "CATEGORIAID")
    private Categoria categoria;

    @Column(name = "PRECIO_VENTA", nullable = false)
    private Double precio_venta;

    @Column(name = "COSTO_PROMEDIO", nullable = false)
    private Double costo_promedio;

    @Column(name = "Stock")
    private Integer stock;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fecha_Creacion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE)
    private List<DetalleVenta> detalles;

    @PrePersist
    public void asignarfechaCreacion() {
        if (this.fecha_Creacion == null) {
            this.fecha_Creacion = LocalDateTime.now();
        }
    }
}
