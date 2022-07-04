package com.companyservice.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * return model if exception occured
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseModel {

    private HttpStatus code;

    private String message;

    private LocalDateTime errorReportingTime;

}