package com.Gurpal.Ecodhan.Repository;

import com.Gurpal.Ecodhan.Entity.CreditRequest;
import com.Gurpal.Ecodhan.Enum.CreditRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest,Long> {
    public List<CreditRequest> findByStatus(CreditRequestStatus creditRequestStatus);
}
