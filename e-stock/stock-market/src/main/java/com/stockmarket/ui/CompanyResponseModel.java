package com.stockmarket.ui;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CompanyResponseModel {

    private Long id;

    private String companyName;

    private String companyCeo;

    private String companyTurnover;

    private String companyWebsite;

    @Column(unique = true)
    private String companyCode;
}
