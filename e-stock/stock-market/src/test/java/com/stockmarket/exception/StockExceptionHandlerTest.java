package com.stockmarket.exception;

import com.stockmarket.ui.ErrorResponseModel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class StockExceptionHandlerTest {

    @InjectMocks
    private StockExceptionHandler stockExceptionHandler;

    @Test
    void handleStockExceptionTest() {

        Exception e = new StockException("stock not available");
        ResponseEntity<ErrorResponseModel> response = stockExceptionHandler.handleStockException(e);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getCode());
        Assertions.assertEquals(response.getBody().getCode(), HttpStatus.BAD_REQUEST);
    }
}
