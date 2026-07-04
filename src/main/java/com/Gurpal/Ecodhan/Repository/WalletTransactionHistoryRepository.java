package com.Gurpal.Ecodhan.Repository;

import com.Gurpal.Ecodhan.Entity.WalletTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionHistoryRepository extends JpaRepository<WalletTransactionHistory,Long> {
}
