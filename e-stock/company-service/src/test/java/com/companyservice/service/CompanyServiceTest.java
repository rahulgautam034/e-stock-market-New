package com.companyservice.service;

import com.companyservice.dto.CompanyDto;
import com.companyservice.entity.Company;
import com.companyservice.exception.CompanyException;
import com.companyservice.proxy.CommonProxy;
import com.companyservice.repository.CompanyRepository;
import com.companyservice.ui.CompanyResponseModel;
import com.companyservice.ui.StockResponseModel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CommonProxy commonProxy;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void init() {
        companyService = new CompanyServiceImpl(companyRepository,modelMapper,commonProxy);
    }


    @Test
    void registerCompanyTest() {
        CompanyDto companyDto = new CompanyDto("test","tim",1000000L,"www.test.com","TEST");
        Assertions.assertThrows(CompanyException.class,()-> companyService.registerCompany(companyDto));
    }

    @Test
    void registerCompanyTest1() {
        CompanyDto companyDto = new CompanyDto("test","tim",10000000000L,"www.test.com","TEST");
        Company responseModel = new Company();
        responseModel.setId("Id122");
        responseModel.setCompanyName(companyDto.getCompanyName());
        responseModel.setCompanyCeo(companyDto.getCompanyCeo());
        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(responseModel);
        CompanyResponseModel res = companyService.registerCompany(companyDto);
        Assertions.assertNotNull(res);
    }

    @Test
    void companyDetailTest() {
        Mockito.when(companyRepository.getByCompanyCode(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(CompanyException.class,()->companyService.getCompanyDetail("123",false));
    }

    @Test
    void companyDetailTest1() {
        Company responseModel = new Company();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("Test");
        Mockito.when(companyRepository.getByCompanyCode(Mockito.any())).thenReturn(responseModel);
        CompanyResponseModel res = companyService.getCompanyDetail(responseModel.getCompanyCode(),true);
        Assertions.assertNotNull(res);

    }

    @Test
    void deleteCompanyTest() {
        Company responseModel = new Company();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("Test");
        Mockito.when(companyRepository.getByCompanyCode(Mockito.any())).thenReturn(responseModel);
        companyService.deleteCompany("TEST");
        Mockito.verify(companyRepository,Mockito.times(1)).getByCompanyCode(Mockito.any());
    }

    @Test
    void getAllTest() {
        List<Company> responseModelList =  new ArrayList<>();
        Company responseModel = new Company();
        responseModel.setId("Id122");
        responseModel.setCompanyName("test");
        responseModel.setCompanyCeo("tim");
        responseModel.setCompanyCode("TEST");
        responseModelList.add(responseModel);
        Mockito.when(companyRepository.findAll()).thenReturn(responseModelList);
        List<CompanyResponseModel> res = companyService.getAll();
        Assertions.assertNotNull(res);
        Assertions.assertEquals(responseModelList.size(),res.size());
    }

    @Test
    void setCompanyLatestStockTest() {
        CompanyResponseModel responseModel = new CompanyResponseModel();
        companyService.setCompanyLatestStock(responseModel,"TEST");
        Mockito.verify(commonProxy).getCompanyStock(Mockito.anyString());
    }
}
