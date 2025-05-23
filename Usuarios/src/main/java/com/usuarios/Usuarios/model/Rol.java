package com.usuarios.Usuarios.model;

import jakarta.persistence.*;


@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;

    @Column
    private String nombre_rol; // Ej: "ADMIN", "CLIENTE"
}
