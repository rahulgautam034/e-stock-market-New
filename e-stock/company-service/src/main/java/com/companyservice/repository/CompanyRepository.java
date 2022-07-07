package com.companyservice.repository;

import com.companyservice.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * company repository for db operations with mongoDB
 */
public interface CompanyRepository extends MongoRepository<Company,Long> {
    /**
     * get company detail by company code
     * @param companyCode
     * @return company detail
     */
    Company getByCompanyCode(String companyCode);
}
