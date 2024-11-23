package com.example.demo_sd.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String company;

    @JsonProperty
    private String description;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private boolean isPublic;

    @JsonProperty
    private boolean isAi;

    @JsonProperty
    private String date;

    @JsonProperty
    private String category;


}
