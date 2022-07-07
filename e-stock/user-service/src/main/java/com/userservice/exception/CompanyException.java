package com.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * company exception
 */
@AllArgsConstructor
@Getter
public class CompanyException extends  RuntimeException {
    private String message;

}
