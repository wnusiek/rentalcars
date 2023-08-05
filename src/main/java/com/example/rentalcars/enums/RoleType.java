package com.example.rentalcars.enums;

public enum RoleType {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private String name;

    RoleType(String name) {
        this.name = name;
    }
}

