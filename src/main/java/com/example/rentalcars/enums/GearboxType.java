package com.example.rentalcars.enums;

public enum GearboxType {
    MANUAL("Manual"),
    AUTOMATIC("Automatic");

    private String name;

    private GearboxType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
