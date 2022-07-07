package com.userservice.proxy;

import com.stockmarket.dto.StockDto;
import com.userservice.ui.StockResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * proxy to use api of STOCK-WS using feign client
 *
 */
@FeignClient(value = "STOCK-WS")
public interface StockProxy {

    /**
     *
     * @param stockDto
     * @return
     */
    @PostMapping("/api/v1.0/market/stock/add")
    StockResponseModel createStock(@RequestBody  StockDto stockDto);

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
    @GetMapping("/api/v1.0/market/stock/get-company-latest-stock/{companyCode}")
    List<StockResponseModel> getCompanyStock(@PathVariable String companyCode);

    /**
     * fetch latest stock of company
     * @param companyCodes
     * @return latest stock in list of company
     */
    @GetMapping("/api/v1.0/market/stock/get-all-stock/{companyCodes}")
    List<StockResponseModel> getCompanyStock(@PathVariable List<String> companyCodes);

    @GetMapping("/api/v1.0/market/stock/get-all-stock/{companyCodes}")
    List<StockResponseModel> getAllStock(@PathVariable List<String> companyCodes);

    @GetMapping("/api/v1.0/market/stock/get/")
    List<StockResponseModel> getAll(@RequestParam String companyCode, @RequestParam String startDate, @RequestParam String endDate);
}
