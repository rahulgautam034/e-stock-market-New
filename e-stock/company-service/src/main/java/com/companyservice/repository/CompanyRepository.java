package com.companyservice.repository;

import com.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * company repository for db operations with mysql db
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    /**
     * get company detail by company code
     * @param companyCode -> {@link -> companyCode}
     * @return company detail
     */
    Company getByCompanyCode(String companyCode);
}
