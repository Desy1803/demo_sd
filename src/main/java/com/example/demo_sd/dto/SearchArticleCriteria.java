package com.example.demo_sd.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@ToString
@AllArgsConstructor
@Getter
@Setter
public class SearchArticleCriteria {
    @JsonProperty
        private Long id;
    @JsonProperty
        private String title;
    @JsonProperty
        private String company;
    @JsonProperty
        private Boolean isPublic;
    @JsonProperty
        private Boolean isAi;
    @JsonProperty
        private String date;
    @JsonProperty
    private String timeUnit;
    @JsonProperty
        private String user;

    @JsonProperty
        private String category;

    @JsonProperty
    private LocalDateTime createdAt;


}
