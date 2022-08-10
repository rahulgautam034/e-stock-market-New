package com.companyservice.exception;

import com.companyservice.ui.ErrorResponseModel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * exception handler
 */
@NoArgsConstructor
@ControllerAdvice
public class CompanyExceptionHandler {

    /**
     * handle expception when thrown
     * @param exception -> throw exception response
     * @return expection custom response
     */
    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<ErrorResponseModel> handleCompanyException(final CompanyException exception){
        final ErrorResponseModel errorResponse = new ErrorResponseModel();

        errorResponse.setCode(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setErrorTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }
}
