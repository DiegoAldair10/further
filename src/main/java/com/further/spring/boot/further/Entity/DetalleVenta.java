package com.further.spring.boot.further.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DETALLEVENTAS")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLEVENTAS")
    @SequenceGenerator(name = "SEQ_DETALLEVENTAS", sequenceName = "SEQ_DETALLEVENTAS", allocationSize = 1)
    @Column(name = "DETALLEVENTAID")
    private Long detalleVentaId;

    @ManyToOne
    @JoinColumn(name = "VENTAID", nullable = false)
    @JsonIgnoreProperties("detalles") // Evita bucle con Venta
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "PRODUCTOID", nullable = false)
    @JsonIgnoreProperties("detalles") // Evita bucle con Producto
    private Producto producto;
    ;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "PRECIOUNITARIO")
    private Double precioUnitario;
    @Transient
    private Double subtotal;


    public Double getSubtotal() {
        if (cantidad == null || precioUnitario == null) {
            return 0.0;
        }
        return cantidad * precioUnitario;
    }
}