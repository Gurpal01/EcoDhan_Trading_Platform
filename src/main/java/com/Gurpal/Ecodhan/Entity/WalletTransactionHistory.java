package com.Gurpal.Ecodhan.Entity;

import com.Gurpal.Ecodhan.Enum.WalletTransactionType;
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
public class WalletTransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Enumerated(EnumType.STRING)
    private WalletTransactionType type; //but,sell,credit_reward,refund,penality,top-up
    private Integer creditAmount;
    private Double moneyAmount;
    private Double moneyAmountAfterTransaction;
    private Integer creditAmountAfterTransaction;
    private Double moneyChange;
    private Integer creditChange;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "walletId",nullable = false)
    private Wallet wallet;

}
