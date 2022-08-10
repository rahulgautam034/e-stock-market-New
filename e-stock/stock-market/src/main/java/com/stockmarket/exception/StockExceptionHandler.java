package com.stockmarket.exception;

import com.stockmarket.ui.ErrorResponseModel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
@NoArgsConstructor
@ControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockException.class)
    public ResponseEntity<ErrorResponseModel> handleStockException(final Exception e){
        final ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setCode(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setErrorTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
