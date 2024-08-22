package com.example.demo.services;

import com.example.demo.models.Categoria;
import com.example.demo.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }


    // Listar todas las categorías
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }


    // Crear una nueva categoría
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Actualizar una categoría existente
    public Categoria actualizarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Eliminar una categoría por ID
    public void eliminarCategoria(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("La categoría con ID " + id + " no existe.");
        }
    }
    // Método para obtener una categoría por su ID
    public Optional<Categoria> getCategoryById(Long id) {
        return categoriaRepository.findById(id);
    }
}