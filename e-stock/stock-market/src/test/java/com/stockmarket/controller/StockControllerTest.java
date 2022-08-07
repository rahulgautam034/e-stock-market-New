package com.stockmarket.controller;

import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
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
        Stock stock = new Stock(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        StockDto stockDto = new StockDto(121.2,"TEST");
        Mockito.when(stockService.createStock(Mockito.any())).thenReturn(stock);
        ResponseEntity<?> res =  stockController.addNewStock(stockDto);
        Assertions.assertNotNull(res);
    }

    @Test
    void getAllStockOfCompanyTest() {
        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        StockResponseModel stock = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockResponseModelList.add(stock);
        Mockito.when(stockService.getAllStockOfCompany(Mockito.any())).thenReturn(stockResponseModelList);
        ResponseEntity<List<StockResponseModel>> res =  stockController.getAllStockOfCompany(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockResponseModelList.size(),res.getBody().size());
    }

    @Test
    void getCompanyLatestStockTest() {
        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        StockResponseModel stock = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        stockResponseModelList.add(stock);
        Mockito.when(stockService.getCompanyLatestStock(Mockito.any())).thenReturn(stockResponseModelList);
        ResponseEntity<List<StockResponseModel>> res =  stockController.getCompanyLatestStock(stock.getCompanyCode());
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockResponseModelList.size(),res.getBody().size());
    }

    @Test
    void getAllStockTest() {
        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        StockResponseModel stock1 = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        StockResponseModel stock2 = new StockResponseModel(2L,132,"ABC","test1","2022-07-13 06:16:44");

        stockResponseModelList.add(stock1);
        stockResponseModelList.add(stock2);
        Mockito.when(stockService.getAllStock()).thenReturn(stockResponseModelList);
        ResponseEntity<List<StockResponseModel>> res =  stockController.getAllStock();
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockResponseModelList.size(),res.getBody().size());
    }

    @Test
    void getLatestStockOfCompaniesTest() {
        List<StockResponseModel> stockResponseModelList = new ArrayList<>();
        StockResponseModel stock1 = new StockResponseModel(1L,121.2,"TEST","test","2022-07-13 05:16:44");
        StockResponseModel stock2 = new StockResponseModel(2L,132,"ABC","test1","2022-07-13 06:16:44");

        stockResponseModelList.add(stock1);
        stockResponseModelList.add(stock2);
        Mockito.when(stockService.getLatestStockOfCompanies(Mockito.any())).thenReturn(stockResponseModelList);
        ResponseEntity<List<StockResponseModel>> res =  stockController.getLatestStockOfCompanies(stockResponseModelList
                .stream().map(StockResponseModel::getCompanyCode)
                .collect(Collectors.toList()));

        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stockResponseModelList.size(),res.getBody().size());
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
        Mockito.when(stockService.getAll(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stock);
        ResponseEntity<List<Stock>> res =  stockController.getAll("TEST",startDate,endDate);
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getBody());
        Assertions.assertEquals(stock.size(),res.getBody().size());
    }

    @Test
    void deleteCompanyStock() {
        stockController.deleteCompanyStock("TEST");
        Mockito.verify(stockService).deleteAllCompanyStock(Mockito.any());
    }
}
