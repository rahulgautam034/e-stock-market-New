package com.companyservice.controller;

import com.companyservice.dto.CompanyDto;
import com.companyservice.entity.Company;
import com.companyservice.proxy.CommonProxy;
import com.companyservice.service.CompanyService;
import com.companyservice.ui.CompanyResponseModel;
import com.companyservice.ui.StockResponseModel;
import com.companyservice.exception.CompanyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * company operations like ->
 * register,company-detail,delete company
 */
@RestController
@RequestMapping("/api/v1.0/market/company/")
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
     * @param companyService
     * @param commonProxy
     */
    public CompanyController(CompanyService companyService,CommonProxy commonProxy){
        this.companyService =companyService;
        this.commonProxy = commonProxy;
    }

    /**
     * register new company
     * @param companyDto
     * @return company response
     */
    @PostMapping("register")
    public ResponseEntity<?> registerCompany(@RequestBody final CompanyDto companyDto) {
        if(companyDto.getCompanyTurnover() <= 100000000){
            throw  new CompanyException("Company Turnover is low");
        }
            Company response =  companyService.registerCompany(companyDto);

            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * fetch company detail
     * @param companyCode
     * @return return company response object
     */
    @GetMapping("info/{companyCode}")
    public ResponseEntity<?> getCompanyDetail(@PathVariable final String companyCode) {
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
    @GetMapping("get-all")
    public ResponseEntity<List<CompanyResponseModel>> getAll() {
        final List<CompanyResponseModel> response =  companyService.getAll();
        if(response != null){
            final List<String> companyCodes = response.stream()
                    .map(company-> company.getCompanyCode())
                    .collect(Collectors.toList());

            final List<StockResponseModel> stocks = commonProxy.getCompanyStock(companyCodes);

            for(CompanyResponseModel companyResponseModel : response){
                final List<StockResponseModel> stockResponseModels = stocks.stream()
                        .filter(stock->
                            stock.getCompanyCode().equals(companyResponseModel.getCompanyCode())
                        ).collect(Collectors.toList());
                companyResponseModel.setStock(stockResponseModels);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * delete company with the help of company code
     * @param companyCode
     * @return response message
     */
    @DeleteMapping("delete/{companyCode}")
    public ResponseEntity<?> deleteCompany(@PathVariable final String companyCode) {
        commonProxy.deleteCompanyStock(companyCode);

        final String response =  companyService.deleteCompany(companyCode);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
