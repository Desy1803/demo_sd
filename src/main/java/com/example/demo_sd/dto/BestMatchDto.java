package com.example.demo_sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BestMatchDto {
    @JsonProperty
    private String function;
    @JsonProperty
    private String keywords;
    @JsonProperty
    private String datatype;

    public BestMatchDto() {
    }

    public String getFunction() {
        return this.function;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}