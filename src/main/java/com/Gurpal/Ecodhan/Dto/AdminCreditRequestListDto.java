package com.Gurpal.Ecodhan.Dto;

import com.Gurpal.Ecodhan.Entity.Company;
import com.Gurpal.Ecodhan.Enum.CreditRequestStatus;


import java.time.LocalDateTime;

public class AdminCreditRequestListDto {
    private Long creditRequestId;
    private Integer amount;
    private String proofUrl;
    private String description;
    private LocalDateTime createdAt;
    private String company;

    public Long getCreditRequestId() {
        return creditRequestId;
    }

    public void setCreditRequestId(Long creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProofUrl() {
        return proofUrl;
    }

    public void setProofUrl(String proofUrl) {
        this.proofUrl = proofUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
