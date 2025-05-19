package com.Ecomark.ecomarket.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.Ecomark.ecomarket.Model.Producto;



import com.Ecomark.ecomarket.Repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(Producto producto){
        if (productoRepository.existsByCodigo (producto.getCodigo())){
        throw new RuntimeException ("El codigo ingresado ya existe");
        }
        return productoRepository.save(producto);
    }

    public List<Producto> listaProductos (){
        return productoRepository.findAll();
    }

    public Producto FindById (long id){
        return productoRepository.findById(id).get();
    }
    
    public Producto save (Producto producto){
        return productoRepository.save(producto);
    }

    public void delete (Long id) {
        productoRepository.deleteById(id);

    }


}