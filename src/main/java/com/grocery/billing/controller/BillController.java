package com.grocery.billing.controller;

import com.grocery.billing.model.Bill;
import com.grocery.billing.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private com.grocery.billing.repository.ProductRepository productRepository;

    @GetMapping
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        // Update stock for each item
        for (com.grocery.billing.model.BillItem item : bill.getItems()) {
            com.grocery.billing.model.Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        bill.setDate(java.time.LocalDateTime.now());
        return billRepository.save(bill);
    }
}
