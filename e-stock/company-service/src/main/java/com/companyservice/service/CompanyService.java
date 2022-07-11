package com.companyservice.service;

import com.companyservice.dto.CompanyDto;
import com.companyservice.ui.CompanyResponseModel;

import java.util.List;

/**
 * company related methods
 */
public interface CompanyService {
    /**
     * register new company
     * @param companyDto -> new company object
     * @return company response
     */
    CompanyResponseModel registerCompany(CompanyDto companyDto);

    /**
     * company detail of one company
     * @param companyCode -> unique code
     * @param isLocal -> check method call is within class or not
     * @return company response object
     */
    CompanyResponseModel getCompanyDetail(String companyCode,Boolean isLocal);

    /**
     * delete a company with the help of company code
     * @param companyCode -> unique code of every company
     * @return message
     */
    String deleteCompany(String companyCode);

    /**
     * get all companies
     * @return list of companies
     */
    List<CompanyResponseModel> getAll();
}
