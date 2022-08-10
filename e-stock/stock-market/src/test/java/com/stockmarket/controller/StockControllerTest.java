package com.stockmarket.controller;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.exception.StockException;
import com.stockmarket.service.StockService;
import com.stockmarket.ui.StockResponseModel;
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
class StockControllerTest {

    @InjectMocks
    private StockController stockController;

    @Mock
    private StockService stockService;


    @Test
    void addStockTest() {
        final Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final StockDto stockDto = new StockDto(121.2,"TEST");
        Mockito.when(stockService.createStock(Mockito.any())).thenReturn(stock);
        final ResponseEntity<?> res =  stockController.addNewStock(stockDto);
        Assertions.assertNotNull(res);
    }

    @Test
    void getAllStockOfCompanyTest() {
        final List<StockResponseModel> stockList = new ArrayList<>();
        final StockResponseModel stock = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockList.add(stock);
        Mockito.when(stockService.getAllStockOfCompany(Mockito.any())).thenReturn(stockList);
        final ResponseEntity<List<StockResponseModel>> res =  stockController.getAllStockOfCompany(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockList.size(),res.getBody().size());
    }

    @Test
    void getCompanyLatestStockTest() {
        final List<StockResponseModel> stockList = new ArrayList<>();
        final StockResponseModel stock = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockList.add(stock);
        Mockito.when(stockService.getCompanyLatestStock(Mockito.any())).thenReturn(stockList);
        final ResponseEntity<List<StockResponseModel>> res =  stockController.getCompanyLatestStock(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockList.size(),res.getBody().size());
    }

    @Test
    void getAllStockTest() {
        final List<StockResponseModel> stockList = new ArrayList<>();
        final StockResponseModel stock1 = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final StockResponseModel stock2 = new StockResponseModel(2L,132,"ABC","test1","2022-07-13 06:16:44");

        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockService.getAllStock()).thenReturn(stockList);
        final ResponseEntity<List<StockResponseModel>> res =  stockController.getAllStock();
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockList.size(),res.getBody().size());
    }

    @Test
    void getLatestStockOfCompaniesTest() {
        final List<StockResponseModel> stockList = new ArrayList<>();
        final StockResponseModel stock1 = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        final StockResponseModel stock2 = new StockResponseModel(2L,132,"ABC","test1","2022-07-13 06:16:44");

        stockList.add(stock1);
        stockList.add(stock2);
        Mockito.when(stockService.getLatestStockOfCompanies(Mockito.any())).thenReturn(stockList);
        final ResponseEntity<List<StockResponseModel>> res =  stockController.getLatestStockOfCompanies(stockList
                .stream().map(StockResponseModel::getCompanyCode)
                .collect(Collectors.toList()));

        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockList.size(),res.getBody().size());
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
        Mockito.when(stockService.getAll(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stock);
        final ResponseEntity<List<Stock>> res =  stockController.getAll("TEST",startDate,endDate);
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stock.size(),res.getBody().size());
    }

    @Test
    void deleteCompanyStock() {
        stockController.deleteCompanyStock("TEST");
        Mockito.verify(stockService).deleteAllCompanyStock(Mockito.any());
    }

    @Test
    void testFallBack(){
        Throwable t = new Throwable("503 error");
        Exception e = new Exception(t);
        Assertions.assertThrows(StockException.class,()->stockController.companyWSFallBack(e));
    }
}
