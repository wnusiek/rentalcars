package com.example.rentalcars.enums;

public enum GearboxType {
    MANUAL("Manualna"),
    AUTOMATIC("Automatyczna");

    private String name;

    GearboxType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
