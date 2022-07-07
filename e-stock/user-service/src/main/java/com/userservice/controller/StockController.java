package com.userservice.controller;

import com.stockmarket.dto.StockDto;
import com.userservice.exception.StockException;
import com.userservice.proxy.StockProxy;
import com.userservice.ui.StockResponseModel;
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

    public StockController(StockProxy stockProxy) {
        this.stockProxy = stockProxy;
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
        try{
            StockResponseModel response =  stockProxy.createStock(stockDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new StockException(e.getMessage());
        }
    }

    /**
     * fetch company detail based on company code
     * @param companyCode
     * @return list with latest stock price
     */
    @GetMapping("get-company-latest-stock/{companyCode}")
    private ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable String companyCode){
        log.info("getCompanyStock using company code");

        try{
            List<StockResponseModel> stock = stockProxy.getCompanyStock(companyCode);
            return  ResponseEntity.status(HttpStatus.OK).body(stock);

        }catch (Exception e){
            throw new StockException(e.getMessage());

        }
    }

    /**
     * fetch companies stock based on company code
     * @param companyCodes
     * @return list of companies stock
     */

    @GetMapping("get-all-stock/{companyCodes}")
    private ResponseEntity<List<StockResponseModel>> getCompanyStock(@PathVariable List<String> companyCodes){
        log.info("getCompanyStock called-> get all stock by companyCodes");

        try{
            List<StockResponseModel> stock = stockProxy.getAllStock(companyCodes);
            return  ResponseEntity.status(HttpStatus.OK).body(stock);

        }catch (Exception e){
            throw  new StockException(e.getMessage());
        }


    }

    /**
     * fetch records as based on variables
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return list of stock
     */
    @GetMapping("get")
    private ResponseEntity<List<StockResponseModel>> getAll(@RequestParam String companyCode, @RequestParam String startDate, @RequestParam String endDate){
        log.info("getCompanyStock called-> fetch records as based on variables");

        try
        {
            List<StockResponseModel> stocks = stockProxy.getAll(companyCode, startDate,endDate);
            return ResponseEntity.status(HttpStatus.OK).body(stocks);
        }catch(Exception e){
            throw  new StockException(e.getMessage());
        }

    }

    /**
     * delete the company stock
     * @param companyCode
     * @return message
     */
    @DeleteMapping("delete/{companyCode}")
    private ResponseEntity<String> deleteCompanyStock(@PathVariable String companyCode) {
        log.info("deleteCompanyStock called");
        try {
            String response = stockProxy.deleteCompanyStock(companyCode);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            throw new StockException(e.getMessage());
        }
    }

    /**
     * show error response if STOCK-WS is down
     * @param e
     * @return fallback message
     */
    public ResponseEntity<?> stockWSFallBack(final Exception e) {
        log.info("companyWSFallBack called");
        return ResponseEntity.ok("within myTestFallBack method. COMPANY-WS is down" + e.toString());
    }


}
