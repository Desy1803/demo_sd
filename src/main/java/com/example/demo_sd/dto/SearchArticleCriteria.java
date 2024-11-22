package com.example.demo_sd.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.LocalDateTime;
@ToString
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
        private String user;

    @JsonProperty
        private String category;

    @JsonProperty
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

        public Boolean getIsPublic() {
            return isPublic;
        }

        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
        }

        public Boolean getIsAi() {
            return isAi;
        }

        public void setIsAi(Boolean isAi) {
            this.isAi = isAi;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }


    public SearchArticleCriteria(Long id, String title, String company, Boolean isPublic, Boolean isAi, String date, String user, String category, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.isPublic = isPublic;
        this.isAi = isAi;
        this.date = date;
        this.user = user;
        this.category = category;
        this.createdAt = createdAt;
    }


}
