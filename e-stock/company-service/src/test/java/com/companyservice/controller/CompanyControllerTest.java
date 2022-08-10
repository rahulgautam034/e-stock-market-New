package com.companyservice.controller;

import com.companyservice.dto.CompanyDto;
import com.companyservice.exception.CompanyException;
import com.companyservice.proxy.CommonProxy;
import com.companyservice.service.CompanyService;
import com.companyservice.ui.CompanyResponseModel;
import com.companyservice.ui.StockResponseModel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    @Mock
    private CommonProxy commonProxy;

    @Test
    void registerCompanyTest() {
        final CompanyDto companyDto = new CompanyDto("test","tim",100_000_000_000L,"www.test.com","TEST");
        final CompanyResponseModel responseModel = new CompanyResponseModel();
        responseModel.setId("Id122");
        responseModel.setCompanyName(companyDto.getCompanyName());
        responseModel.setCompanyCeo(companyDto.getCompanyCeo());
        Mockito.when(companyService.registerCompany(Mockito.any())).thenReturn(responseModel);
        final ResponseEntity<?> res = companyController.registerCompany(companyDto);
        Assertions.assertNotNull(res);

    }

    @Test
    void companyDetailTest() {
        final CompanyResponseModel responseModel = new CompanyResponseModel();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("Test");
        Mockito.when(companyService.getCompanyDetail(Mockito.any(),Mockito.any())).thenReturn(responseModel);
        final ResponseEntity<?> res = companyController.getCompanyDetail(responseModel.getCompanyCeo(),false);
        Assertions.assertNotNull(res);

    }

    @Test
    void companyDetailTest1() {
        final CompanyResponseModel responseModel = new CompanyResponseModel();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("Test");
        Mockito.when(companyService.getCompanyDetail(Mockito.any(),Mockito.any())).thenReturn(responseModel);
        final ResponseEntity<?> res = companyController.getCompanyDetail(responseModel.getCompanyCeo(),true);
        Assertions.assertNotNull(res);

    }

    @Test
    void getAllTest() {
        final List<CompanyResponseModel> responseModelList =  new ArrayList<>();
        final CompanyResponseModel responseModel = new CompanyResponseModel();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("TEST");
        responseModelList.add(responseModel);
        final List<StockResponseModel> stockResponseModels =  new ArrayList<>();
        final StockResponseModel stockResponse = new StockResponseModel(1L,120.0,"TEST",
                "test","2022-07-13 12:33:55");
        stockResponseModels.add(stockResponse);
        Mockito.when(companyService.getAll()).thenReturn(responseModelList);
        Mockito.when(commonProxy.getCompanyStock(responseModelList
                .stream()
                .map(CompanyResponseModel::getCompanyCode)
                .collect(Collectors.toList())
        )).thenReturn(stockResponseModels);
        final ResponseEntity<List<CompanyResponseModel>> res = companyController.getAll();
        Assertions.assertNotNull(res);
    }

    @Test
    void deleteCompanyTest() {
        companyController.deleteCompany("TEST");
        Mockito.verify(companyService,Mockito.times(1)).deleteCompany(Mockito.any());
    }

    @Test
    void testFallBack(){
        Throwable t = new Throwable("503 error");
        Exception e = new Exception(t);
        Assertions.assertThrows(CompanyException.class,()->companyController.stockWSFallBack(e));
    }
}
