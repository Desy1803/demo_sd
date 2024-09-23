package com.example.demo_sd.dto;

public class BestMatchDto {
    private String function;
    private String keywords;
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