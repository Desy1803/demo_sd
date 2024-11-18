package com.example.demo_sd.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailVerification {
    @JsonProperty("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
