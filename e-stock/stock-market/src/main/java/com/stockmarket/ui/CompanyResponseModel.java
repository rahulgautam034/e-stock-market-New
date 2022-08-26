package com.stockmarket.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@NoArgsConstructor
@Getter
@Setter
public class CompanyResponseModel {

    private Long id;

    private String companyName;

    private String companyCeo;

    private String companyTurnover;

    private String companyWebsite;

    @Column(unique = true)
    private String companyCode;
}
