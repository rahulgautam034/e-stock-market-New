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

    private static final long serialVersionUID = 9053315727906717006L;

    private String message;

}
