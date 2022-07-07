package com.userservice.controller;

import com.userservice.dto.CompanyDto;
import com.userservice.exception.CompanyException;
import com.userservice.exception.StockException;
import com.userservice.proxy.CompanyProxy;
import com.userservice.ui.CompanyResponseModel;
import com.userservice.ui.StockResponseModel;
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
public class CompanyController {

    /**
     * common proxy to call other service methods
     */
    private CompanyProxy companyProxy;

    /**
     * for constructor di of variables
     * @param companyProxy
     */
    public CompanyController(CompanyProxy companyProxy){
        this.companyProxy = companyProxy;
    }

    /**
     * register new company
     * @param companyDto
     * @return company response
     */
    @PostMapping("register")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDto) {
        log.info("register new company");

        try{
            CompanyResponseModel response =  companyProxy.registerCompany(companyDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new CompanyException(e.getMessage());
        }

    }

    /**
     * fetch company detail
     * @param companyCode
     * @return return company response object
     */
    @GetMapping("info/{companyCode}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable String companyCode) {
        log.info("get company detail by companyCode:{}",companyCode);
        try{
            final CompanyResponseModel response =  companyProxy.getCompanyDetail(companyCode);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new CompanyException(e.getMessage());
        }


    }

    /**
     * fetch all companies
     * @return list of companies
     */
    @GetMapping("get-all")
    public ResponseEntity<List<CompanyResponseModel>> getAll() {
        log.info("get all companies");
        try{
            final List<CompanyResponseModel> response =  companyProxy.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new CompanyException(e.getMessage());
        }
    }

    /**
     * delete company with the help of company code
     * @param companyCode
     * @return response message
     */
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<?> deleteCompany(@PathVariable final String companyCode) {
        log.info("delete company by companycode :{}",companyCode);

        try {

            final String response = companyProxy.deleteCompany(companyCode);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new CompanyException(e.getMessage());
        }

    }
}
