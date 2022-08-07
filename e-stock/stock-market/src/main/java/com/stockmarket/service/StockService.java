package com.stockmarket.service;


import com.stockmarket.dto.StockDto;
import com.stockmarket.entity.Stock;
import com.stockmarket.ui.StockResponseModel;

import java.util.List;

/**
 * stock abstract methods
 */
public interface StockService {

    /**
     * create new stock of company
     * @param stockDto -> stock object
     * @return saved stock
     */
    Stock createStock(StockDto stockDto);

    /**
     * get all stock as per based on variables
     * @param companyCode -> unique code of each company
     * @param startDate -> stock search start date
     * @param endDate -> stock search end date
     * @return list of stock
     */
    List<Stock> getAll(String companyCode, String startDate, String endDate);

    /**
     * delete all stock of company
     * @param companyCode -> unique code of every company
     * @return  delete success message
     */
    String deleteAllCompanyStock(String companyCode);

    /**
     * fetch company latest stock based on company code
     * @param companyCode -> unique code of every company
     * @return  company latest one stock only
     */
    List<StockResponseModel> getCompanyLatestStock(String companyCode);

    /**
     * fetch all stock of company by company code
     * @param companyCode -> unique code of every comany
     * @return list of all stock
     */
    List<StockResponseModel> getAllStockOfCompany(String companyCode);

    /**
     * fetch all stock
     * @return list of all stock
     */
    List<StockResponseModel> getAllStock();

    /**
     * fetch all stock based on muliptle company codes
     * @param companyCodes -> unique code of every company
     * @return list of companies stock
     */
    List<StockResponseModel> getLatestStockOfCompanies(List<String> companyCodes);


}
