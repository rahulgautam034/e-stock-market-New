package com.userservice.exception;

import com.stockmarket.ui.ErrorResponseModel;
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
    public ResponseEntity<ErrorResponseModel> handleCompanyException(final Exception exception){
        final ErrorResponseModel errorResponseModel = new ErrorResponseModel();

        errorResponseModel.setCode(HttpStatus.BAD_REQUEST);
        errorResponseModel.setMessage(exception.getMessage());
        errorResponseModel.setErrorReportingTime(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(errorResponseModel);

    }
}
