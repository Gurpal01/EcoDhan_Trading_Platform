package com.Gurpal.Ecodhan.Entity;

import com.Gurpal.Ecodhan.Enum.CreditRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditRequestId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private CreditRequestStatus status; //pending,approved,rejected
    private String proofUrl;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    @ManyToOne
    @JoinColumn(name = "companyId",nullable = false)
    private Company company;

}
