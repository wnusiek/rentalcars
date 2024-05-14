package com.example.rentalcars.Exceptions;

public class CustomerAdditionException extends RuntimeException {
    public CustomerAdditionException(String message, Throwable cause) {
        super(message, cause);
    }
}
