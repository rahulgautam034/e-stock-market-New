package com.userservice.controller;

import com.userservice.dto.StockDto;
import com.userservice.exception.StockException;
import com.userservice.proxy.StockProxy;
import com.userservice.ui.StockResponseModel;
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
@RequestMapping("api/v1.0/market/user/stock")
@Log4j2
public class StockController {

    private final StockProxy stockProxy;

    public StockController(final StockProxy stockProxy) {
        this.stockProxy = stockProxy;
    }


    /***
     * add stock of register company
     * @param stockDto-> request object
     * @return succes-> saved stock/ error-> return error message
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @PostMapping("add")
    public ResponseEntity<?> addNewStock(@RequestBody final StockDto stockDto) {
        log.info("addNewStock called");
            final StockResponseModel response =  stockProxy.createStock(stockDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fetch company detail based on company code
     * @param companyCode->unique code of every company
     * @return list with latest stock price
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("get-company-latest-stock/{companyCode}")
    public ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable final String companyCode){
        log.info("getCompanyStock using company code");
            final List<StockResponseModel> stock = stockProxy.getCompanyStock(companyCode);
            return  ResponseEntity.status(HttpStatus.OK).body(stock);
    }

    /**
     * fetch all stock of one company based on company code
     * @param companyCode->unique code of every company
     * @return list of companies stock
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("all/{companyCode}")
    public ResponseEntity<List<StockResponseModel>> getAllStockOfCompany(@PathVariable final String companyCode){
        log.info("getCompanyStock called-> get all stock by company code");
            final List<StockResponseModel> stock = stockProxy.getAllStockOfCompany(companyCode);
            return  ResponseEntity.status(HttpStatus.OK).body(stock);
    }

    /**
     * fetch all companies stock based on company codes
     * @param companyCodes->unique code of every company
     * @return list of companies stock
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("get-all-stock/{companyCodes}")
    public ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable final List<String> companyCodes){
        log.info("getCompanyStock called-> get all stock by companyCodes");
            final List<StockResponseModel> stock = stockProxy.getAllStock(companyCodes);
            return  ResponseEntity.status(HttpStatus.OK).body(stock);
    }

    /**
     * fetch records as based on variables
     * @param companyCode->unique code of every company
     * @param startDate -> search start date
     * @param endDate -> search end date
     * @return list of stock
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("get")
    public ResponseEntity<List<StockResponseModel>> getAll(@RequestParam final String companyCode,
                                                           @RequestParam String startDate,
                                                           final@RequestParam String endDate){
        log.info("getCompanyStock called-> fetch records as based on variables");
            final List<StockResponseModel> stocks = stockProxy.getAll(companyCode, startDate,endDate);
            return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    /**
     * delete the company stock
     * @param companyCode->unique code of every company
     * @return message
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<String> deleteCompanyStock(@PathVariable final String companyCode) {
        log.info("deleteCompanyStock called");
            final String response = stockProxy.deleteCompanyStock(companyCode);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * show error response if STOCK-WS is down
     * @param e exception response
     * @return fallback message
     */
    public ResponseEntity<?> stockWSFallBack(final Exception e) {
        log.info("stockWSFallBack called");
        final String message = e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage() : "within myTestFallBack method. STOCK-WS is down";
        throw new StockException(message);
    }


}
