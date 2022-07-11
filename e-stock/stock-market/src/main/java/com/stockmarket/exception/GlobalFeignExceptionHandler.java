package com.stockmarket.exception;

import com.stockmarket.ui.ErrorResponseModel;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalFeignExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseModel> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setMessage(e.getMessage());
        errorResponseModel.setCode(HttpStatus.BAD_REQUEST);
        errorResponseModel.setErrorReportingTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseModel);
    }
}
