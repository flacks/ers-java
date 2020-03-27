package com.revature.enums.user;

public enum Role {
    Employee (1),
    Manager (2),
    Admin (3);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
