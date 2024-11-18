package com.example.demo_sd.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {
    @JsonProperty
    private String name;
    @JsonProperty
    private String symbol;
    @JsonProperty
    private String category;

    public Company(String name, String symbol, String category) {
        this.name = name;
        this.symbol = symbol;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCategory() {
        return category;
    }
}

