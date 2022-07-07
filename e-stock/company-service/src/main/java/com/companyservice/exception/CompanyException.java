package com.companyservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * company exception
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyException extends  RuntimeException {

    private String message;

}
