package com.companyservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "company")
public class Company {

    @Id()
    private String id;

    private String companyName;

    private String companyCeo;

    private Long companyTurnover;

    private String companyWebsite;

    private String companyCode;

}
