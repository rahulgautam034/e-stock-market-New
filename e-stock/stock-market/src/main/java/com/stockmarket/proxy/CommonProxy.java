package com.stockmarket.proxy;

import com.stockmarket.ui.CompanyResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * proxy to use api of COMPANY-WS using feign client
 *
 */
@FeignClient(value = "COMPANY-WS")
public interface CommonProxy {
    /**
     * fetch company code to validate company is regsiter or not
     * @param companyCode -> unique code of each company
     * @return regsitered company
     */
    @GetMapping("/api/v1.0/market/company/info/{companyCode}/{withStock}")
    CompanyResponseModel getCompanyDetail(@PathVariable String companyCode,@PathVariable Boolean withStock);
}
