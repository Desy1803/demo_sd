package com.example.demo_sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserRequest {

        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;

        @JsonProperty("email")
        private String email;

}

