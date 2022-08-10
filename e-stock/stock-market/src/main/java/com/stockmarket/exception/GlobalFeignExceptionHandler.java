package com.stockmarket.exception;

import com.stockmarket.ui.ErrorResponseModel;
import feign.FeignException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@NoArgsConstructor
@RestControllerAdvice
public class GlobalFeignExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseModel> handleFeignStatusException(final FeignException e, final HttpServletResponse response) {
        final ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setCode(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
