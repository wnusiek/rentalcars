package com.example.rentalcars.enums;

public enum ReservationStatus {

    RESERVED("Auto zarezerwowane"),
    CANCELLED("Rezerwacja anulowana");

    private String name;

    ReservationStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
