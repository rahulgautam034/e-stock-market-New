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
     * @param stockDto
     * @return saved stock
     */
    Stock createStock(StockDto stockDto);

    /**
     * get all stock as per based on variables
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return list of stock
     */
    List<Stock> getAll(String companyCode, String startDate, String endDate);

    /**
     * delete all stock of company
     * @param companyCode
     * @return
     */
    String deleteAllCompanyStock(String companyCode);

    /**
     * fetch company stock based on company code
     * @param companyCode
     * @return list of company stock
     */
    List<StockResponseModel> getCompanyStock(String companyCode);

    /**
     * fetch all stock
     * @return list of all stock
     */
    List<StockResponseModel> getAllStock();

    /**
     * fetch all stock based on muliptle company codes
     * @param companyCodes
     * @return
     */
    List<StockResponseModel> getAllStock(List<String> companyCodes);


}
