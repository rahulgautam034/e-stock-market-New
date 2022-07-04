package com.companyservice.repository;

import com.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * company repository for db operations
 */
public interface CompanyRepository extends JpaRepository<Company,Long> {
    /**
     * ge company detail by company code
     * @param companyCode
     * @return company detail
     */
    Company getByCompanyCode(String companyCode);
}
