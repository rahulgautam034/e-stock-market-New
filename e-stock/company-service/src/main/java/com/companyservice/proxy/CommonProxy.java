package com.companyservice.proxy;

import com.companyservice.ui.StockResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * proxy to use api of STOCK-WS using feign client
 *
 */
@FeignClient(value = "STOCK-WS")
public interface CommonProxy {
    /**
     * delete all stock of company
     * @param companyCode
     * @return success message
     */
    @DeleteMapping("/api/v1.0/market/stock/delete/{companyCode}")
    String deleteCompanyStock(@PathVariable String companyCode);

    /**
     * get all stock of current company
     * @param companyCode
     * @return list of stock of company
     */
    @GetMapping("/api/v1.0/market/stock/get-company-stock/{companyCode}")
    List<StockResponseModel> getCompanyStock(@PathVariable String companyCode);

    /**
     * fetch latest stock of company
     * @param companyCodes
     * @return latest stock in list of company
     */
    @GetMapping("/api/v1.0/market/stock/get-all-stock/{companyCodes}")
    List<StockResponseModel> getCompanyStock(@PathVariable List<String> companyCodes);
}
