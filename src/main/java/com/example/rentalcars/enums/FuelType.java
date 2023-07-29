package com.example.rentalcars.enums;

public enum FuelType {
    GAS("Gas"),
    PETROL("Petrol"),
    DIESEL("Diesel");

    private String name;

    private FuelType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
