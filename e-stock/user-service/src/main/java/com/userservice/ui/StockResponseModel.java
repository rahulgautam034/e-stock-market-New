package com.userservice.ui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponseModel {

    private Long id;

    private double stockPrice;

    private String companyCode;

    private String companyName;

    private String createdDate;

}
