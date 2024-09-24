package com.example.demo_sd.enumerations;

public enum TimeSeriesType {
    TIME_SERIES_DAILY("TIME_SERIES_DAILY"),
    TIME_SERIES_WEEKLY("TIME_SERIES_WEEKLY"),
    TIME_SERIES_MONTHLY("TIME_SERIES_MONTHLY");

    private final String value;

    TimeSeriesType(String value) {
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

