package com.company.enums;

public enum Status {

    OPEN("Open"), IN_PROGRESS("In Progress"), COMPLETE("Complete");

    private final String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }
}
