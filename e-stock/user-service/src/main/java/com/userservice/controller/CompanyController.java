package com.userservice.controller;

import com.userservice.dto.CompanyDto;
import com.userservice.exception.CompanyException;
import com.userservice.proxy.CompanyProxy;
import com.userservice.ui.CompanyResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * company operations like ->
 * register,company-detail,delete company
 */
@RestController
@RequestMapping("/api/v1.0/market/user/company/")
@Log4j2
@CrossOrigin(origins = "*")
public class CompanyController {

    /**
     * common proxy to call other service methods
     */
    private final CompanyProxy companyProxy;

    /**
     * for constructor di of variables
     * @param companyProxy -> feign company service di
     */
    public CompanyController(CompanyProxy companyProxy){
        this.companyProxy = companyProxy;
    }

    /**
     * register new company
     * @param companyDto -> request object
     * @return company response
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @PostMapping("register")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDto) {
        log.info("register new company");
            final CompanyResponseModel response =  companyProxy.registerCompany(companyDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fetch company detail
     * @param companyCode->unique code of every company
     * @return return company response object
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("info/{companyCode}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable String companyCode) {
        log.info("get company detail by companyCode:{}",companyCode);
            final CompanyResponseModel response =  companyProxy.getCompanyDetail(companyCode,true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fetch all companies
     * @return list of companies
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @GetMapping("get-all")
    public ResponseEntity<List<CompanyResponseModel>> getAll() {
        log.info("get all companies");
            final List<CompanyResponseModel> response =  companyProxy.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * delete company with the help of company code
     * @param companyCode->unique code of every company
     * @return response message
     */
    @CircuitBreaker(name = "companyWSCircuitBreaker", fallbackMethod = "companyWSFallBack")
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<?> deleteCompany(@PathVariable final String companyCode) {
        log.info("delete company by companycode :{}",companyCode);
        final String response = companyProxy.deleteCompany(companyCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * show error response if COMPANY-WS is down
     * @param e exception
     * @return fallback message
     */
    private ResponseEntity<?> companyWSFallBack(final Exception e) {
        log.info("companyWSFallBack called");
        String message = (e.getMessage() != null && !e.getMessage().isEmpty()) ? e.getMessage() : "within myTestFallBack method. COMPANY-WS is down";
        throw new CompanyException(message);
    }
}
