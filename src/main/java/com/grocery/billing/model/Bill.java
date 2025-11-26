package com.grocery.billing.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    private LocalDateTime date;
    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BillItem> items;
}
