package com.companyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String companyName;

    private String companyCeo;

    private Long companyTurnover;

    private String companyWebsite;

    private String companyCode;

}
