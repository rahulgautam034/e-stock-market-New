package com.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * company exception
 */
@AllArgsConstructor
@Getter
public class CompanyException extends  RuntimeException {
    private String message;

}
