package com.example.order.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String paymentMethod;

//    @Enumerated(EnumType.STRING)
//    private PaymentStatus status;

    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}