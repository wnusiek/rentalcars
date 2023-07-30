package com.example.rentalcars.enums;

public enum BodyType {
    HATCHBACK("Hatchback"),
    WAGON("Wagon"),
    SEDAN("Sedan"),
    COUPE("Coupe"),
    SUV("Suv"),
    CONVERTIBLE("Convertible"),
    PICKUP("Pickup"),
    MINIVAN("Minivan");

    private String name;

    private BodyType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
