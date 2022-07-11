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
    public CompanyController(CompanyService companyService,CommonProxy commonProxy){
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
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDto) {
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
    @GetMapping("info/{companyCode}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable final String companyCode) {
        log.info("get company detail by companyCode:{}",companyCode);
        final CompanyResponseModel response =  companyService.getCompanyDetail(companyCode,false);

        if(response != null){
            final List<StockResponseModel> stocks = commonProxy.getCompanyStock(companyCode);
            if(!stocks.isEmpty()){

                response.setStock(stocks);

            }
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

    private void setCompanyStock(List<CompanyResponseModel> companies,List<StockResponseModel> stocks){

        for(final CompanyResponseModel companyResponseModel : companies){
            final List<StockResponseModel> stockResponseModels = stocks.stream()
                    .filter(stock->
                            stock.getCompanyCode().equals(companyResponseModel.getCompanyCode())
                    ).collect(Collectors.toList());
            companyResponseModel.setStock(stockResponseModels);
        }
    }

    /**
     * show error response if STOCK-WS is down
     * @param e -> exception response
     * @return fallback message
     */
    public ResponseEntity<?> stockWSFallBack(final Exception e) {
        log.info("companyWSFallBack called");
        String message = !e.getMessage().contains("503") && e.getMessage() != null ? e.getMessage() : "within stockWSFallBack method. STOCK-WS is down";
        throw new CompanyException(message);
    }
}
