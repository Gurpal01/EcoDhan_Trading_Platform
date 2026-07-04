package com.Gurpal.Ecodhan.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private Double moneyBalance;
    private Integer greenCredit;
    @OneToOne
    @JoinColumn(name = "companyId",nullable = false)
    private Company company;
    @OneToMany(mappedBy = "wallet",cascade = CascadeType.ALL)
    private List<WalletTransactionHistory> walletTransactionHistoryList = new ArrayList<>();
}
