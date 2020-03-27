package com.revature.enums.reimbursement;

public enum Status {
    New (1),
    Approved (2),
    Unapproved (3);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
