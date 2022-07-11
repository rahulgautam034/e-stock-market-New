package com.userservice.proxy;

import com.userservice.dto.StockDto;
import com.userservice.ui.CompanyResponseModel;
import com.userservice.ui.StockResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * proxy to use api of STOCK-WS using feign client
 *
 */
@FeignClient(value = "STOCK-WS",path = "/api/v1.0/market/stock/")
public interface StockProxy {

    /**
     *
     * @param stockDto -> user request stock object
     * @return stock response for user
     */
    @PostMapping("add")
    StockResponseModel createStock(@RequestBody StockDto stockDto);

    /**
     * delete all stock of company
     * @param companyCode -> unique code of every company
     * @return success message
     */
    @DeleteMapping("delete/{companyCode}")
    String deleteCompanyStock(@PathVariable String companyCode);

    /**
     * get all stock of current company
     * @param companyCode -> unique code of every company
     * @return list of stock of company
     */
    @GetMapping("get-company-latest-stock/{companyCode}")
    List<StockResponseModel> getCompanyStock(@PathVariable String companyCode);

    @GetMapping("get-all-company-stock/{companyCode}")
    List<StockResponseModel> getAllStockOfCompany(@PathVariable String companyCode);

    @GetMapping("get-all-stock/{companyCodes}")
    List<StockResponseModel> getAllStock(@PathVariable List<String> companyCodes);

    @GetMapping("get")
    List<StockResponseModel> getAll(@RequestParam String companyCode, @RequestParam String startDate, @RequestParam String endDate);
}
