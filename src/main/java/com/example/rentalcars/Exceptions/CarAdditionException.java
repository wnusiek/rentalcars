package com.example.rentalcars.Exceptions;

public class CarAdditionException extends RuntimeException{
    public CarAdditionException(String message, Throwable cause) {
        super(message, cause);
    }
}
