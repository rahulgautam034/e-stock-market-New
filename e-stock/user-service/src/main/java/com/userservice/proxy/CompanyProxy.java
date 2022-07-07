package com.userservice.proxy;

import com.userservice.dto.CompanyDto;
import com.userservice.ui.CompanyResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * proxy to use api of COMPANY-WS using feign client
 *
 */
@FeignClient(value = "COMPANY-WS")
public interface CompanyProxy {

    /**
     * register new company
     * @param companyDto
     * @return register company response
     */
    @PostMapping("/api/v1.0/market/company/register")
    CompanyResponseModel registerCompany(@RequestBody CompanyDto companyDto);

    /**
     * fetch company code to validate company is regsiter or not
     * @param companyCode
     * @return regsitered company
     */
    @GetMapping("/api/v1.0/market/company/info/{companyCode}")
    CompanyResponseModel getCompanyDetail(@PathVariable String companyCode);

    @GetMapping("/api/v1.0/market/company/get-all")
    List<CompanyResponseModel> getAll();

    @DeleteMapping("/api/v1.0/market/company/delete/{companyCode}")
    String deleteCompany(@PathVariable String companyCode);
}
