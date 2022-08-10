package com.userservice.exception;

import com.userservice.ui.ErrorResponseModel;
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
        final ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setCode(HttpStatus.BAD_REQUEST);
        errorResponseModel.setMessage(e.getMessage());
        errorResponseModel.setErrorTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponseModel);
    }
}
