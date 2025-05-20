package com.Ecomark.ecomarket.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique=true, length= 25 , nullable = false) 
    private String nom_usuario;

    @Column( nullable = true)
    private Date fechaVenta;

    @Column ( nullable = true)
    private BigDecimal total;

    @Column(nullable = false)
    private String email;
    
    @ManyToOne
    @JoinColumn(name ="codigo")
    private Cupon cupon;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalle;
}
