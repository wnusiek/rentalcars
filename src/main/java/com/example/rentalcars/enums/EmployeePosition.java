package com.example.rentalcars.enums;

public enum EmployeePosition {
    EMPLOYEE("EMPLOYEE"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private String name;

    EmployeePosition(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }


}
