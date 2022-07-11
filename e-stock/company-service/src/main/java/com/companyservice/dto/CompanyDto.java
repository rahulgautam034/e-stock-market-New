package com.companyservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {

    private String companyName;

    private String companyCeo;

    private Long companyTurnover;

    private String companyWebsite;

    private String companyCode;

}
