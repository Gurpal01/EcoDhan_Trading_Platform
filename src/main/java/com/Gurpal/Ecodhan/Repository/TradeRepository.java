package com.Gurpal.Ecodhan.Repository;

import com.Gurpal.Ecodhan.Entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade,Long> {
    List<Trade> findByBuyerCompanyOrSellerCompany(String buyerCompany,String sellerCompany);
}
