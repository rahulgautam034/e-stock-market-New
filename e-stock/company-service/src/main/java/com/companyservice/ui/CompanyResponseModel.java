package com.companyservice.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * company response for user
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyResponseModel {

    private String id;

    private String companyName;

    private String companyCeo;

    private String companyTurnover;

    private String companyWebsite;

    private String companyCode;

    private List<StockResponseModel> stock = new ArrayList<>();
}
