package com.Ecomark.ecomarket.model;

import java.math.BigDecimal; 

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor




public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column (unique =false , length = 20, nullable = false)
    private String nombreProducto;


    @Column(nullable = false)
    private Integer cantidad;

    @Column( precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
    
}