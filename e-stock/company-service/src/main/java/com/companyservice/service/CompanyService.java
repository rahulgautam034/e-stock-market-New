package com.companyservice.service;

import com.companyservice.dto.CompanyDto;
import com.companyservice.entity.Company;
import com.companyservice.ui.CompanyResponseModel;

import java.util.List;

/**
 * company related methods
 */
public interface CompanyService {
    /**
     * register new company
     * @param companyDto
     * @return company response
     */
    CompanyResponseModel registerCompany(CompanyDto companyDto);

    /**
     * company detail of one company
     * @param companyCode
     * @param isLocal
     * @return company response object
     */
    CompanyResponseModel getCompanyDetail(String companyCode,Boolean isLocal);

    /**
     * delete a company with the help of company code
     * @param companyCode
     * @return message
     */
    String deleteCompany(String companyCode);

    /**
     * get all companies
     * @return list of companies
     */
    List<CompanyResponseModel> getAll();
}
