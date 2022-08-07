package com.stockmarket.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockResponseModel {

    private Long id;

    private double stockPrice;

    private String companyCode;

    private String companyName;

    private String createdDate;

}
