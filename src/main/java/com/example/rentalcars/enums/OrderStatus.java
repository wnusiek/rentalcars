package com.example.rentalcars.enums;

public enum OrderStatus {

    CANCELLED("Anulowane");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
