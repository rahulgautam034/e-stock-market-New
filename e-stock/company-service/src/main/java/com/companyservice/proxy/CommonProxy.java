package com.companyservice.proxy;

import com.companyservice.ui.StockResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * proxy to use api of STOCK-WS using feign client
 *
 */
@FeignClient(value = "STOCK-WS", path = "/api/v1.0/market/stock/")
public interface CommonProxy {

    /**
     * delete all stock of company
     * @param companyCode company unique code
     * @return success message
     */
    @DeleteMapping("delete/{companyCode}")
    String deleteCompanyStock(@PathVariable String companyCode);

    /**
     * get all stock of current company
     * @param companyCode company unique code
     * @return list of stock of company
     */
    @GetMapping("get-company-latest-stock/{companyCode}")
    List<StockResponseModel> getCompanyStock(@PathVariable String companyCode);

    /**
     * fetch latest stock of company
     * @param companyCodes unique company code
     * @return latest stock in list of company
     */
    @GetMapping("get-all-stock/{companyCodes}")
    List<StockResponseModel> getCompanyStock(@PathVariable List<String> companyCodes);

}
