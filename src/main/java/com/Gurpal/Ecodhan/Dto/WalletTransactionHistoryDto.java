package com.Gurpal.Ecodhan.Dto;

import com.Gurpal.Ecodhan.Entity.Wallet;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class WalletTransactionHistoryDto {
    private String type; //buy,sell,credit_reward,refund,penality,top-up
    private Integer creditAmount;
    private Double moneyAmount;
    private Double moneyAmountAfterTransaction;
    private Integer creditAmountAfterTransaction;
    private LocalDateTime createdAt;
    private Double moneyChange;
    private Integer creditChange;

    public Double getMoneyChange() {
        return moneyChange;
    }

    public void setMoneyChange(Double moneyChange) {
        this.moneyChange = moneyChange;
    }

    public Integer getCreditChange() {
        return creditChange;
    }

    public void setCreditChange(Integer creditChange) {
        this.creditChange = creditChange;
    }

    public Double getMoneyAmountAfterTransaction() {
        return moneyAmountAfterTransaction;
    }

    public void setMoneyAmountAfterTransaction(Double moneyAmountAfterTransaction) {
        this.moneyAmountAfterTransaction = moneyAmountAfterTransaction;
    }

    public Integer getCreditAmountAfterTransaction() {
        return creditAmountAfterTransaction;
    }

    public void setCreditAmountAfterTransaction(Integer creditAmountAfterTransaction) {
        this.creditAmountAfterTransaction = creditAmountAfterTransaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
