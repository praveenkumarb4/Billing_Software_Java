package com.grocery.billing.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Double quantity;
    private Double price; // Price at the time of billing
}
