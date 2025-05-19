package com.Ecomark.ecomarket.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Ecomark.ecomarket.Model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Long> {
    boolean existsByCodigo(int codigo);
}
