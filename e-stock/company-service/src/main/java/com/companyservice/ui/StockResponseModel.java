package com.companyservice.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * stock response for user
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockResponseModel {

    private String id;

    private double stockPrice;

    private String companyCode;

    private String companyName;

    private String createdDate;

    public void setId(Long id) {
        this.id = id.toString();
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
