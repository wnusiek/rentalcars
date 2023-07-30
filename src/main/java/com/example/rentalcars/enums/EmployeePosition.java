package com.example.rentalcars.enums;

public enum EmployeePosition {
    EMPLOYEE("Employee"),
    MANAGER("Manager");

    private String name;

    private EmployeePosition(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }


}
