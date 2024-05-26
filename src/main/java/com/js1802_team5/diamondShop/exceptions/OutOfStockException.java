package com.js1802_team5.diamondShop.exceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }
}
