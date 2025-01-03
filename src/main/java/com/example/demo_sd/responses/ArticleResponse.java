package com.example.demo_sd.responses;

import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.services.KeycloakService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String company;

    @JsonProperty
    private String description;


    @JsonProperty
    private boolean isPublic;

    @JsonProperty
    private boolean isAi;

    @JsonProperty
    private String timeUnit;

    @JsonProperty
    private String category;

    @JsonProperty
    private String authorUsername;

    @JsonProperty
    private String authorEmail;

    public static ArticleResponse toModel(ArticleEntity articleEntity) {
        String authorUsername = KeycloakService.getUser(articleEntity.getAuthor()).toRepresentation().getUsername();
        String authorEmail = KeycloakService.getUser(articleEntity.getAuthor()).toRepresentation().getEmail();
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setId(articleEntity.getId());
        articleResponse.setTitle(articleEntity.getTitle());
        articleResponse.setCompany(articleEntity.getCompany());
        articleResponse.setDescription(articleEntity.getDescription());
        articleResponse.setPublic(articleEntity.isPublic());
        articleResponse.setAi(articleEntity.isAi());
        articleResponse.setTimeUnit(articleEntity.getTimeUnit());
        articleResponse.setCategory(articleEntity.getCategory());
        articleResponse.setAuthorEmail(authorEmail);
        articleResponse.setAuthorUsername(authorUsername);
        return articleResponse;
    }

}
