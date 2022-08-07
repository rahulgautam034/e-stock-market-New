package com.stockmarket.exception;

import feign.FeignException;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;

@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class GlobalFeignExceptionHandlerTest {

    @InjectMocks
    private GlobalFeignExceptionHandler exceptionHandler;

    @Mock
    private FeignException feignException;

    @Mock
    private HttpServletResponse response;

    @Test
    void handleFeignStatusExceptionTest() {
        exceptionHandler.handleFeignStatusException(feignException,response);
    }
}
