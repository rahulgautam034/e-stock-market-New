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
    private CompanyResponseModel companyResponse;

    private final ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    void init() {
        stockService = new StockServiceImpl(stockRepository,modelMapper,commonProxy);
    }

    @Test
    void createStockTest() {
        final Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final StockDto stockDto = new StockDto(121.2,"TEST");
        Mockito.when(commonProxy.getCompanyDetail(Mockito.any(),Mockito.any())).thenReturn(companyResponse);
        Mockito.when(stockRepository.save(Mockito.any())).thenReturn(stock);

        final Stock res =  stockService.createStock(stockDto);
        Assertions.assertNotNull(res);
    }

    @Test
    void getAllTest() {
        final String startDate = "2022-07-10";
        final String endDate = "2022-07-13";
        final List<Stock> stock = new ArrayList<>();
        final Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stock.add(stock1);
        stock.add(stock2);
        Mockito.when(stockRepository.findByCriteria(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stock);
        final List<Stock> res =  stockService.getAll("TEST",startDate,endDate);
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stock.size(),res.size());
    }

    @Test
    void deleteCompanyStock() {
        final List<Stock> stock = new ArrayList<>();
        final Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stock.add(stock1);
        stock.add(stock2);
        Mockito.when(stockRepository.findAllByCompanyCode(Mockito.any())).thenReturn(stock);
        stockService.deleteAllCompanyStock("TEST");
        Mockito.verify(stockRepository).findAllByCompanyCode(Mockito.any());
    }

    @Test
    void getCompanyLatestStockTest() {
        final List<Stock> stockList = new ArrayList<>();
        final Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockList.add(stock);
        Mockito.when(stockRepository.findLatestRecord(Mockito.any())).thenReturn(stockList);
        final List<StockResponseModel> res =  stockService.getCompanyLatestStock(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getAllStockTest() {
        final List<Stock> stockList = new ArrayList<>();
        final Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final Stock stock2 = new Stock(2L,132,"ABC","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockRepository.findAll()).thenReturn(stockList);
        final List<StockResponseModel> res =  stockService.getAllStock();
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getAllStockOfCompanyTest() {
        final List<Stock> stockList = new ArrayList<>();
        final Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockRepository.findAllByCompanyCode(Mockito.any())).thenReturn(stockList);
        final List<StockResponseModel> res =  stockService.getAllStockOfCompany("TEST");
        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }

    @Test
    void getLatestStockOfCompaniesTest() {
        final List<Stock> stockList = new ArrayList<>();
        final Stock stock1 = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final Stock stock2 = new Stock(2L,132,"TEST","test1","2022-07-13 06:16:44");
        stockList.add(stock1);
        stockList.add(stock2);

        final List<String> ids = stockList
                .stream().map(Stock::getCompanyCode)
                .collect(Collectors.toList());
        Mockito.when(stockRepository.findAllByCompanyCodes(Mockito.any())).thenReturn(stockList);
        final List<StockResponseModel> res =  stockService.getLatestStockOfCompanies(ids);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(stockList.size(),res.size());
    }


}
