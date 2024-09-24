package com.example.demo_sd.enumerations;

public enum DataType {
    DATA_TYPE_CSV("csv"),
    DATA_TYPE_JSON("json");

    private final String value;

    DataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}