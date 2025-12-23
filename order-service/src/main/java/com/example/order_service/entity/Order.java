package com.example.order_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order"
            ,cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
   // @JsonManagedReference
    private List<OrderItems> items;
}
