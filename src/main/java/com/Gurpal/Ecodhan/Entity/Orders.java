package com.Gurpal.Ecodhan.Entity;

import com.Gurpal.Ecodhan.Enum.OrderStatus;
import com.Gurpal.Ecodhan.Enum.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //open,partially completed,completed,cancelled
    @Enumerated(EnumType.STRING)
    private OrderType type; //buy or sell
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "companyId",nullable = false)
    private Company company;
}
