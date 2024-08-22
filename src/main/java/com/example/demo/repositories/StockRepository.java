package com.example.demo.repositories;

import com.example.demo.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

  //  List<Stock> findByEstado(String estado);

    // Métodos personalizados para búsqueda por nombre, categoría, talla, y precio
    List<Stock> findByNombreContaining(String nombre);

//    List<Stock> findByCategoria(String categoria);

//    List<Stock> findByTalla(String talla);

//    List<Stock> findByPrecioBetween(Double minPrecio, Double maxPrecio);
// Buscar por nombre que contenga un valor específico

    // Buscar por cédula que coincida exactamente
    List<Stock> findByCedula(String cedula);

    // Buscar por nombre que contenga un valor específico y cédula que coincida exactamente
    List<Stock> findByNombreContainingAndCedula(String nombre, String cedula);




}
