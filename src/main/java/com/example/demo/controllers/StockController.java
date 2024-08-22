package com.example.demo.controllers;

import com.example.demo.CONFIG.EXCEPTION.ResourceNotFoundException;
import com.example.demo.models.Categoria;
import com.example.demo.models.Stock;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private CategoriaService categoryService;

    // Listar todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<Stock>> listStock() {
        List<Stock> stockList = stockService.listStock();
        return ResponseEntity.ok(stockList);
    }

    // Registrar un nuevo producto
   /* @PostMapping("/add")
    public ResponseEntity<String> addStock(
            @RequestParam String cedula,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String sotck_inicial,
            @RequestParam String fecha,
            @RequestParam String ubicacion,
            @RequestParam Long categoria_id,
            @RequestParam(value = "foto", required = false) MultipartFile foto) throws IOException {

        // Buscar la categoría por ID
        Optional<Categoria> categoryOptional = categoryService.getCategoryById(categoria_id);
        if (!categoryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoría no encontrada");
        }

        // Crear y asignar la categoría al stock
        Categoria categoria = categoryOptional.get();
        Set<Categoria> categorias = new HashSet<>();
        categorias.add(categoria);

        Stock stock = Stock.builder()
                .cedula(cedula)
                .nombre(nombre)
                .apellido(apellido)
                .Stock_inicial(sotck_inicial)
                .fecha(fecha)
                .ubicacion(ubicacion)
                .categorias(categorias) // Asignar la categoría
                .build();

        stockService.saveProduct(stock, foto);
        return ResponseEntity.ok("Stock added successfully");
    }*/

    // Actualizar un producto existente
  /*  @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStock(
            @PathVariable Long id,
            @RequestParam String cedula,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String sotck_inicial,
            @RequestParam String fecha,
            @RequestParam String ubicacion,
            @RequestParam Long categoria_id,
            @RequestParam(value = "foto", required = false) MultipartFile foto) throws IOException {

        // Buscar la categoría por ID
        Optional<Categoria> categoryOptional = categoryService.getCategoryById(categoria_id);
        if (!categoryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoría no encontrada");
        }

        // Crear y asignar la categoría al stock
        Categoria categoria = categoryOptional.get();
        Set<Categoria> categorias = new HashSet<>();
        categorias.add(categoria);

        Stock stock = Stock.builder()
                .cedula(cedula)
                .nombre(nombre)
                .apellido(apellido)
                .Stock_inicial(sotck_inicial)
                .fecha(fecha)
                .ubicacion(ubicacion)
                .categorias(categorias) // Asignar la categoría
                .build();

        stockService.actualizarStock(id, stock, foto);
        return ResponseEntity.ok("Stock updated successfully");
    }*/

    // Consultar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stockOptional = stockService.getProductById(id);
        if (stockOptional.isPresent()) {
            return ResponseEntity.ok(stockOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Consultar la foto de un producto por ID
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getStockPhoto(@PathVariable Long id) {
        Optional<Stock> stockOptional = stockService.getProductById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            byte[] photo = stock.getFoto();
            if (photo != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.CONTENT_TYPE, "image/jpeg");
                return new ResponseEntity<>(photo, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Búsqueda de productos por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Stock>> searchStock(@RequestParam(required = false) String nombre) {
        List<Stock> stockList = stockService.buscarZapatos(nombre);
        return ResponseEntity.ok(stockList);
    }

    //
    // Crear un nuevo producto en stock
    @PostMapping("/crear")
    public ResponseEntity<Stock> crearStock(@RequestBody Stock stock) {
        Stock nuevoStock = stockService.crearStock(stock);
        return ResponseEntity.ok(nuevoStock);
    }

    // Actualizar un producto existente en stock
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Stock> actualizarStock(@PathVariable Long id, @RequestBody Stock stockDetalles) {
        Optional<Stock> stockExistente = stockService.obtenerStockPorId(id);
        if (stockExistente.isPresent()) {
            Stock stock = stockExistente.get();
            stock.setCedula(stock.getCedula());
            stock.setNombre(stockDetalles.getNombre());
            stock.setCategorias(stockDetalles.getCategorias());
            stock.setApellido(stockDetalles.getApellido());
            stock.setStock_inicial(stockDetalles.getStock_inicial());
            stock.setUbicacion(stockDetalles.getUbicacion());
            stock.setFecha(stockDetalles.getFecha());
            // Actualizar otros campos según sea necesario

            Stock stockActualizado = stockService.actualizarStock(stock);
            return ResponseEntity.ok(stockActualizado);
        } else {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
    }
    // Búsqueda de productos por nombre y cédula
        @GetMapping("/buscar")
        public List<Stock> buscarPorNombreYCedula(@RequestParam(required = false) String nombre,
                                                  @RequestParam(required = false) String cedula) {
            return stockService.buscarPorNombreYCedula(nombre, cedula);
        }
    // Eliminar un producto existente por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarStock(@PathVariable Long id) {
        try {
            stockService.eliminarStock(id);
            return ResponseEntity.ok("Producto eliminado exitosamente.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al eliminar el producto.");
        }
    }

}