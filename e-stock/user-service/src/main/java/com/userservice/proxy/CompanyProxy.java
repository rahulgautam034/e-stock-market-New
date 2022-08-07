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
@FeignClient(value = "COMPANY-WS",path = "/api/v1.0/market/company/")
public interface CompanyProxy {

    /**
     * register new company
     * @param companyDto -> new company object
     * @return register company response
     */
    @PostMapping("register")
    CompanyResponseModel registerCompany(@RequestBody CompanyDto companyDto);

    /**
     * fetch company code to validate company is regsiter or not
     * @param companyCode -> unique code of every company
     * @return regsitered company
     */
    @GetMapping("info/{companyCode}/{withStock}")
    CompanyResponseModel getCompanyDetail(@PathVariable String companyCode,@PathVariable Boolean withStock);

    @GetMapping("get-all")
    List<CompanyResponseModel> getAll();

    @DeleteMapping("delete/{companyCode}")
    String deleteCompany(@PathVariable String companyCode);
}
