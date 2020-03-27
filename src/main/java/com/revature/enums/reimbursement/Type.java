package com.revature.enums.reimbursement;

public enum Type {
    Lodging (1),
    Travel (2),
    Food (3),
    Other (4);

    private int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
