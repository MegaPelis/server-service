package com.megapelis.server.model.entity;

public enum Status {
    ACTIVE(true),
    INACTIVE(false);

    private  final boolean value;
    Status(boolean value) {
        this.value = value;
    }
    boolean getValue() {
        return this.value;
    }
}
