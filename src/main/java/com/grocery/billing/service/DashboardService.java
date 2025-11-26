package com.grocery.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private BillService billService;

    public Map<String, Object> getDashboardStats(Long shopId) {
        Map<String, Object> stats = new HashMap<>();
        
        long totalOrders = billService.getTotalOrders(shopId);
        Double totalSales = billService.getTotalSales(shopId);

        stats.put("totalOrders", totalOrders);
        stats.put("totalSales", totalSales);
        
        return stats;
    }
}
