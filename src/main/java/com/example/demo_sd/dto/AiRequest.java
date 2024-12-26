package com.example.demo_sd.dto;

import com.example.demo_sd.utils.CustomLocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public record AiRequest<T>(@JsonProperty(required = true) String company,
                           @JsonProperty(required = true) String category,
                           @JsonProperty(required = false)
                           @JsonDeserialize(using = CustomLocalDateDeserializer.class)LocalDate date,
                           @JsonProperty(required = false) T dataOfCompany,
                           @JsonProperty(required = true) String getSourcesFromGoogle) {
}
