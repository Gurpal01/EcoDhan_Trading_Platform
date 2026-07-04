package com.Gurpal.Ecodhan.Entity;

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
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;
    private String buyerCompany;
    private String sellerCompany;
    private Double price;
    private Integer quantity;
    @CreationTimestamp
    private LocalDateTime createAt;
    @ManyToOne
    @JoinColumn(name = "buyOrderId",nullable = false)
    private Orders buyOrder;
    @ManyToOne
    @JoinColumn(name = "sellOrderId",nullable = false)
    private Orders sellOrder;
}
