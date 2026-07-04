package com.Gurpal.Ecodhan.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    private String companyName;
    @OneToOne
    @JoinColumn(name = "userId" , nullable = false)
    private User user;
    @OneToOne(mappedBy = "company",cascade = CascadeType.ALL)
    private Wallet wallet;
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Orders> orders;
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<CreditRequest> creditRequests;

}
