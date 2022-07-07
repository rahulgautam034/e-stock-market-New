package com.userservice.exception;

import com.stockmarket.ui.ErrorResponseModel;
import com.userservice.exception.StockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockException.class)
    public ResponseEntity<ErrorResponseModel> handleStockException(Exception e){
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setCode(HttpStatus.BAD_REQUEST);
        errorResponseModel.setMessage(e.getMessage());
        errorResponseModel.setErrorReportingTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponseModel);
    }
}
