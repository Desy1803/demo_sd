package com.example.demo_sd.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleRequest {

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty
    private String category;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }
}
