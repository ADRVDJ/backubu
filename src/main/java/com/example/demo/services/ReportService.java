package com.example.demo.services;

import com.example.demo.repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ShoeRepository shoeRepository;

    public Map<String, Object> generateInventoryReport() {
        Map<String, Object> report = new HashMap<>();

        // Número total de productos
        long totalProducts = shoeRepository.count();
        report.put("totalProducts", totalProducts);


        return report;
    }
}