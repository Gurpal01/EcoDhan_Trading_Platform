package com.Gurpal.Ecodhan.Repository;

import com.Gurpal.Ecodhan.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
