package com.stockmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockException extends RuntimeException{

    private String message;
}
