package com.stockmarket.controller;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.proxy.CommonProxy;
import com.stockmarket.service.StockService;
import com.stockmarket.ui.CompanyResponseModel;
import com.stockmarket.ui.StockResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * handle stock related operations
 * like -> add,delete,get
 */
@RestController
@RequestMapping("api/v1.0/market/stock/")
@Log4j2
public class StockController {

    private final StockService stockService;
    private final CommonProxy commonProxy;

    public StockController(StockService stockService,CommonProxy commonProxy) {
        this.stockService = stockService;
        this.commonProxy = commonProxy;
    }


    /***
     * add stock of register company
     * @param stockDto
     * @return succes-> saved stock/ error-> return error message
     */
    @PostMapping("add")
    //@CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    private ResponseEntity<?> addNewStock(@RequestBody StockDto stockDto) {
        log.info("addNewStock called");
        final CompanyResponseModel companyDetail = commonProxy.getCompanyDetail(stockDto.getCompanyCode());
        if(companyDetail != null && companyDetail.getCompanyCode() != null){
            stockDto.setCompanyName(companyDetail.getCompanyName());
            Stock response =  stockService.createStock(stockDto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            log.error("Company not Register in our database.Please First register company then add stock price");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company not Register in our database.Please First register company then add stock price");
        }

    }

    /**
     * fetch company detail based on company code
     * @param companyCode
     * @return list with latest stock price
     */
    @GetMapping("get-company-stock/{companyCode}")
    private ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable String companyCode){
        log.info("getCompanyStock using company code");

        List<StockResponseModel> stock = stockService.getCompanyStock(companyCode);

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch all companies stock
     * @return list of stock
     */
    @GetMapping("get-all-stock")
    private ResponseEntity<List<StockResponseModel>> getCompanyStock(){
        log.info("getCompanyStock called-> get all stock");

        List<StockResponseModel> stock = stockService.getAllStock();

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch companies stock based on company code
     * @param companyCodes
     * @return list of companies stock
     */

    @GetMapping("get-all-stock/{companyCodes}")
    private ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable List<String> companyCodes){
        log.info("getCompanyStock called-> get all stock by companyCodes");

        List<StockResponseModel> stock = stockService.getAllStock(companyCodes);

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch records as based on variables
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return list of stock
     */
    @GetMapping("get")
    private ResponseEntity<List<Stock>> getAll(@RequestParam String companyCode, @RequestParam String startDate, @RequestParam String endDate){
        log.info("getCompanyStock called-> fetch records as based on variables");

        List<Stock> stocks = stockService.getAll(companyCode, startDate,endDate);

        return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    /**
     * delete the company stock
     * @param companyCode
     * @return message
     */
    @DeleteMapping("delete/{companyCode}")
    private ResponseEntity<String> deleteCompanyStock(@PathVariable String companyCode){
        log.info("deleteCompanyStock called");

        String response = stockService.deleteAllCompanyStock(companyCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * show error response if COMPANY-WS is down
     * @param e
     * @return fallback message
     */
    public ResponseEntity<?> stockWSFallBack(final Exception e) {
        log.info("companyWSFallBack called");
        return ResponseEntity.ok("within myTestFallBack method. COMPANY-WS is down" + e.toString());
    }


}
