package com.grocery.billing.controller;

import com.grocery.billing.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalOrders = billRepository.count();
        Double totalSales = billRepository.getTotalSales();
        
        if (totalSales == null) {
            totalSales = 0.0;
        }

        stats.put("totalOrders", totalOrders);
        stats.put("totalSales", totalSales);
        
        return stats;
    }
}
