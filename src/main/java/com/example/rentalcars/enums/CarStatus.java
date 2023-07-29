package com.example.rentalcars.enums;

public enum CarStatus {
    AVAILABLE("Available"),
    HIRED("Hired"),
    UNAVAILABLE("Unavailable");

    private String name;

    private CarStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
