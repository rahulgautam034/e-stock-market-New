package com.stockmarket.ui;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class CompanyResponseModel {

    private String id;

    private String companyName;

    private String companyCeo;

    private String companyTurnover;

    private String companyWebsite;

    @Column(unique = true)
    private String companyCode;
}
