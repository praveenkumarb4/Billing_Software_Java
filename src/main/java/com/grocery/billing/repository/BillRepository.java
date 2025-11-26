package com.grocery.billing.repository;

import com.grocery.billing.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @org.springframework.data.jpa.repository.Query("SELECT SUM(b.totalAmount) FROM Bill b")
    Double getTotalSales();
    
    java.util.List<Bill> findByShopId(Long shopId);
    
    @org.springframework.data.jpa.repository.Query("SELECT SUM(b.totalAmount) FROM Bill b WHERE b.shopId = :shopId")
    Double getTotalSalesByShopId(Long shopId);
}
