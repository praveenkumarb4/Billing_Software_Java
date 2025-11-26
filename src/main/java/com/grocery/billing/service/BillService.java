package com.grocery.billing.service;

import com.grocery.billing.model.Bill;
import com.grocery.billing.model.BillItem;
import com.grocery.billing.model.Product;
import com.grocery.billing.repository.BillRepository;
import com.grocery.billing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Bill> getAllBills(Long shopId) {
        if (shopId != null) {
            return billRepository.findByShopId(shopId);
        }
        return billRepository.findAll();
    }

    @Transactional
    public Bill createBill(Bill bill) {
        // Validate and update stock for each item
        for (BillItem item : bill.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProduct().getId()));
            
            // Check if sufficient stock is available
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() + 
                    ". Available: " + product.getStock() + ", Requested: " + item.getQuantity());
            }
            
            // Deduct stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Set bill date
        bill.setDate(LocalDateTime.now());
        
        // Save and return bill
        return billRepository.save(bill);
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }

    public Double getTotalSales(Long shopId) {
        Double totalSales;
        if (shopId != null) {
            totalSales = billRepository.getTotalSalesByShopId(shopId);
        } else {
            totalSales = billRepository.getTotalSales();
        }
        return totalSales != null ? totalSales : 0.0;
    }

    public long getTotalOrders(Long shopId) {
        if (shopId != null) {
            return billRepository.findByShopId(shopId).size();
        }
        return billRepository.count();
    }
}
