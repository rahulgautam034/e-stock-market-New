package com.stockmarket.controller;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.exception.StockException;
import com.stockmarket.service.StockService;
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
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }


    /***
     * add stock of register company
     * @param stockDto user request object
     * @return succes-> saved stock/ error-> return error message
     */
    @PostMapping("add")
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    public ResponseEntity<?> addNewStock(@RequestBody final StockDto stockDto) {
        log.info("addNewStock called");
        final Stock response =  stockService.createStock(stockDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fetch company detail based on company code
     * @param companyCode unique code of company
     * @return list with latest stock price
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get-all-company-stock/{companyCode}")
    public ResponseEntity<List<StockResponseModel>> getAllStockOfCompany(@PathVariable final String companyCode){
        log.info("getCompanyStock using company code");

        final List<StockResponseModel> stock = stockService.getAllStockOfCompany(companyCode);

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch latest stock based on company code
     * @param companyCode unique code of company
     * @return list with latest stock price
     */

    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get-company-latest-stock/{companyCode}")
    public ResponseEntity<List<StockResponseModel>> getCompanyLatestStock(@PathVariable final String companyCode){
        log.info("getCompanyStock using company code");

        final List<StockResponseModel> stock = stockService.getCompanyLatestStock(companyCode);

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch all companies stock
     * @return list of stock
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get-all-stock")
    public ResponseEntity<List<StockResponseModel>> getAllStock(){
        log.info("getCompanyStock called-> get all stock");

        final List<StockResponseModel> stock = stockService.getAllStock();

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch companies stock based on company code
     * @param companyCodes unique code of company
     * @return list of companies stock
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get-all-stock/{companyCodes}")
    public ResponseEntity<List<StockResponseModel>> getLatestStockOfCompanies(@PathVariable final List<String> companyCodes){
        log.info("getCompanyStock called-> get all stock by companyCodes");

        final List<StockResponseModel> stock = stockService.getLatestStockOfCompanies(companyCodes);

        return  ResponseEntity.status(HttpStatus.OK).body(stock);

    }

    /**
     * fetch records as based on variables
     * @param companyCode unique code of company
     *  @param startDate -> search startDate
     *  @param endDate-> search endDate
     * @return list of stock
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get")
    public ResponseEntity<List<Stock>> getAll(@RequestParam final String companyCode,
                                              @RequestParam final String startDate,
                                              @RequestParam final String endDate){
        log.info("getCompanyStock called-> fetch records as based on variables");
        final List<Stock> stocks = stockService.getAll(companyCode, startDate,endDate);
        return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    /**
     * delete the company stock
     * @param companyCode unique code of company
     * @return message
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<String> deleteCompanyStock(@PathVariable final String companyCode){
        log.info("deleteCompanyStock called");

        final String response = stockService.deleteAllCompanyStock(companyCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * show error response if COMPANY-WS is down
     * @param e exception
     * @return fallback message
     */
    public ResponseEntity<?> companyWSFallBack(final Exception e) {
        log.info("companyWSFallBack called");
        final String message = e.getMessage() != null ? e.getMessage() : "within stockWSFallBack method. COMPANY-WS is down";

        throw new StockException(message);
    }


}
