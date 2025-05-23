package com.usuarios.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuarios.Usuarios.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion,Long> {

    
}
