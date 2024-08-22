package com.example.demo.repositories;

import com.example.demo.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT DISTINCT s.nombre FROM Stock s")
    List<String> findDistinctCategories();

}
