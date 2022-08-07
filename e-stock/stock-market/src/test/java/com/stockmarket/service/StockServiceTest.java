package com.stockmarket.service;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.proxy.CommonProxy;
import com.stockmarket.repository.StockRepository;
import com.stockmarket.serviceImpl.StockServiceImpl;
import com.stockmarket.ui.CompanyResponseModel;
import com.stockmarket.ui.StockResponseModel;
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
import java.util.stream.Collectors;

@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private CommonProxy commonProxy;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CompanyResponseModel companyResponseModel;

    private final ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void init() {
        stockService = new StockServiceImpl(stockRepository,modelMapper,commonProxy);
    }

    @Test
    void createStockTest() {
        Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        StockDto stockDto = new StockDto(121.2,"TEST");
        Mockito.when(commonProxy.getCompanyDetail(Mockito.any(),Mockito.any())).thenReturn(companyResponseModel);
        Mockito.when(stockRepository.save(Mockito.any())).thenReturn(stock);

        Stock res =  stockService.createStock(stockDto);
        Assertions.assertNotNull(res);
    }

    @Test
    void getAllTest() {
        String startDate = "2022-07-10";
        String endDate = "2022-07-13";
        List<Stock> stock = new ArrayList<>();
        Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stock.add(stock1);
        stock.add(stock2);
        Mockito.when(stockRepository.findByCriteria(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stock);
        List<Stock> res =  stockService.getAll("TEST",startDate,endDate);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stock.size(),res.size());
    }

    @Test
    void deleteCompanyStock() {
        List<Stock> stock = new ArrayList<>();
        Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stock.add(stock1);
        stock.add(stock2);
        Mockito.when(stockRepository.findAllByCompanyCode(Mockito.any())).thenReturn(stock);
        stockService.deleteAllCompanyStock("TEST");
        Mockito.verify(stockRepository).findAllByCompanyCode(Mockito.any());
    }

    @Test
    void getCompanyLatestStockTest() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockList.add(stock);
        Mockito.when(stockRepository.findLatestRecord(Mockito.any())).thenReturn(stockList);
        List<StockResponseModel> res =  stockService.getCompanyLatestStock(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getAllStockTest() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        Stock stock2 = new Stock(2L,132,"ABC","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockRepository.findAll()).thenReturn(stockList);
        List<StockResponseModel> res =  stockService.getAllStock();
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getAllStockOfCompanyTest() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockRepository.findAllByCompanyCode(Mockito.any())).thenReturn(stockList);
        List<StockResponseModel> res =  stockService.getAllStockOfCompany("TEST");
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getLatestStockOfCompaniesTest() {
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);

        List<String> ids = stockList
                .stream().map(Stock::getCompanyCode)
                .collect(Collectors.toList());
        Mockito.when(stockRepository.findAllByCompanyCodes(Mockito.any())).thenReturn(stockList);
        List<StockResponseModel> res =  stockService.getLatestStockOfCompanies(ids);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }


}
