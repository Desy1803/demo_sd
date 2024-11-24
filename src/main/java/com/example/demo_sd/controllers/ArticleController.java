package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.SearchArticleCriteria;
import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.repositories.ArticleRepository;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.requests.ArticleRequest;
import com.example.demo_sd.responses.ArticleResponse;
import com.example.demo_sd.services.ArticlesService;
import com.example.demo_sd.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin()
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeycloakService keycloakService;


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> createArticleEntity(@RequestBody ArticleRequest article) throws UnsupportedEncodingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        byte[] byteImage = article.getImageUrl().getBytes("UTF-8");
        LocalDateTime now = LocalDateTime.now();
        ArticleEntity articleEntity = new ArticleEntity(null, article.getTitle(), article.getDescription(), article.getCompany(), username,  byteImage ,article.getDate(),article.isPublic(), article.getCategory(), article.isAi(),now, now);
        System.out.println("Sending "+ articleEntity);
        articleRepository.save(articleEntity);
        System.out.println("Saved article");
        return ResponseEntity.ok(articleEntity);
    }

    @GetMapping("/get-image")
    public ResponseEntity<byte[]> getImage(@RequestParam(required = false) String articleId) {
        System.out.println("Getting image");
        ArticleEntity article = articleRepository.findById(Long.parseLong(articleId)).orElse(null);

        if (article != null && article.getImageUrl() != null) {
            return ResponseEntity.ok(article.getImageUrl());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


    @GetMapping("/public")
    public ResponseEntity<?> getAllPublicArticles(@RequestParam(required = false) Long id,
                                                                @RequestParam(required = false) String title,
                                                                @RequestParam(required = false) String company,
                                                                @RequestParam(required = false) Boolean isPublic,
                                                                @RequestParam(required = false) Boolean isAi,
                                                                @RequestParam(required = false) String date,
                                                                @RequestParam(required = false) String user,
                                                                @RequestParam(required = false) String category,
                                                                @RequestParam(required = false) String createdAt) {
        try {
            System.out.println("Getting public articles ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime parsedCreatedAt = null;
            if(createdAt!= null) {
                parsedCreatedAt = LocalDateTime.parse(createdAt, formatter);
            }

            SearchArticleCriteria criteria = new SearchArticleCriteria(
                    id,
                    title,
                    company,
                    true,
                    isAi,
                    date,
                    user,
                    category,
                    parsedCreatedAt);
            List<ArticleEntity> articlesEntity = articleRepository.findAll(ArticlesService.getSpecification(criteria));

            List<ArticleResponse> articlesResponse = articlesEntity.stream()
                    .map(ArticleResponse::toModel)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(articlesResponse);

        } catch (Exception e) {
            System.out.println("Error while fetching public articles: {}"+ e.getMessage()+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching public articles.");
        }
    }


    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getArticlesById(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String isPublic,
            @RequestParam(required = false) String isAi,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String createdAt
    ) {
        try {
            System.out.println("Getting private articles ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime parsedCreatedAt = (createdAt != null && !createdAt.isEmpty())
                    ? LocalDateTime.parse(createdAt, formatter)
                    : null;
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            boolean isAi1 = Boolean.parseBoolean(isAi);
            SearchArticleCriteria criteria = new SearchArticleCriteria(
                    id,
                    title,
                    company,
                    null,
                    isAi1,
                    date,
                    username,
                    category,
                    parsedCreatedAt);

            List<ArticleEntity> articlesEntity = articleRepository.findAll(ArticlesService.getSpecification(criteria));
            List<ArticleResponse> articlesResponse = articlesEntity.stream()
                    .map(ArticleResponse::toModel)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(articlesResponse);

        } catch (Exception e) {
            System.out.println("Error while fetching articles by user: {}"+ e.getMessage()+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user articles.");
        }
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> updateArticleEntity( @RequestParam(required = true) String id,
                                                              @RequestParam(required = false) String title,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam(required = false) String company,
                                                              @RequestParam(required = false) String isPublic,
                                                              @RequestParam(required = false) String isAi,
                                                              @RequestParam(required = false) String date,
                                                              @RequestParam(required = false) String category) {
        System.out.println("Update article ");
        boolean isPublic1 = Boolean.parseBoolean(isPublic);
        boolean isAi1 = Boolean.parseBoolean(isAi);
        return articleRepository.findById(Long.valueOf(id))
                .map(article -> {
                    article.setTitle(title);
                    article.setDescription(description);
                    article.setCompany(company);
                    article.setTimeUnit(date);
                    article.setUpdatedAt(LocalDateTime.now());
                    article.setPublic(isPublic1);
                    article.setAi(isAi1);
                    article.setCategory(category);
                    return ResponseEntity.ok(articleRepository.save(article));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteArticleEntity(@RequestParam Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    articleRepository.delete(article);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
