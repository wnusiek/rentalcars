package com.example.rentalcars.enums;

public enum CarStatus {
    AVAILABLE("Dostępny"),
    HIRED("Wypożyczony"),
    UNAVAILABLE("Niedostępny");

    private String name;

    CarStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
