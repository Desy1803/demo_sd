package com.example.demo_sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PaginatedResponse<T> {

    @JsonProperty(required = true)
    private List<T> data;

    @JsonProperty(required = true)
    private int totalCount;

    @JsonProperty(required = true)
    private int currentPage;

    @JsonProperty(required = true)
    private int totalPages;
}
