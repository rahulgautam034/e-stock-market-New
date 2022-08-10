package com.companyservice.controller;

import com.companyservice.dto.CompanyDto;
import com.companyservice.exception.CompanyException;
import com.companyservice.proxy.CommonProxy;
import com.companyservice.service.CompanyService;
import com.companyservice.ui.CompanyResponseModel;
import com.companyservice.ui.StockResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * company operations like ->
 * register,company-detail,delete company
 */
@RestController
@RequestMapping("/api/v1.0/market/company/")
@Log4j2
@CrossOrigin(origins="*")
public class CompanyController {

    /**
     * company service
     */
    private final CompanyService companyService;

    /**
     * common proxy to call other service methods
     */
    private final CommonProxy commonProxy;

    /**
     * for constructor di of variables
     * @param companyService include company operation's
     * @param commonProxy for feign call -> one service to other
     */
    public CompanyController(final CompanyService companyService,final CommonProxy commonProxy){
        this.companyService =companyService;
        this.commonProxy = commonProxy;
    }

    /**
     * register new company
     * @param companyDto-> user request object
     * @return company response
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @PostMapping("register")
    public ResponseEntity<?> registerCompany(@RequestBody final CompanyDto companyDto) {
        log.info("register new company");
        final CompanyResponseModel response =  companyService.registerCompany(companyDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fetch company detail
     * @param companyCode -> unique code of company
     * @return return company response object
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("info/{companyCode}/{withStock}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable final String companyCode,@PathVariable final Boolean withStock) {
        log.info("get company detail by companyCode:{}",companyCode);
        final CompanyResponseModel response =  companyService.getCompanyDetail(companyCode,false);

        if(response != null && withStock){
            companyService.setCompanyLatestStock(response,companyCode);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * fetch all companies
     * @return list of companies
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @GetMapping("get-all")
    public ResponseEntity<List<CompanyResponseModel>> getAll() {
        log.info("get all companies");
        final List<CompanyResponseModel> response =  companyService.getAll();
        if(response != null){
            final List<String> companyCodes = response.stream()
                    .map(CompanyResponseModel::getCompanyCode)
                    .collect(Collectors.toList());

            final List<StockResponseModel> stocks = commonProxy.getCompanyStock(companyCodes);
            setCompanyStock(response,stocks);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * delete company with the help of company code
     * @param companyCode -> unique code of company
     * @return response message
     */
    @CircuitBreaker(name = "stockWSCircuitBreaker", fallbackMethod = "stockWSFallBack")
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<?> deleteCompany(@PathVariable final String companyCode) {
        log.info("delete company by companycode :{}",companyCode);
        commonProxy.deleteCompanyStock(companyCode);

        final String response =  companyService.deleteCompany(companyCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    private void setCompanyStock(final List<CompanyResponseModel> companies,final List<StockResponseModel> stocks){

        for(final CompanyResponseModel responseModel : companies){
            final List<StockResponseModel> stockResponse = stocks.stream()
                    .filter(stock->
                            stock.getCompanyCode().equals(responseModel.getCompanyCode())
                    ).collect(Collectors.toList());
            responseModel.setStock(stockResponse);
        }
    }

    /**
     * show error response if STOCK-WS is down
     * @param exception -> exception response
     * @return fallback message
     */
    public ResponseEntity<?> stockWSFallBack(final Exception exception) {
        log.info("companyWSFallBack called");
        final String message = !exception.getMessage().contains("503") && exception.getMessage() != null ? exception.getMessage() : "within stockWSFallBack method. STOCK-WS is down";
        throw new CompanyException(message);
    }
}
