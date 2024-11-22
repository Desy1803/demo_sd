package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.SearchArticleCriteria;
import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.repositories.ArticleRepository;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.requests.ArticleRequest;
import com.example.demo_sd.services.ArticlesService;
import com.example.demo_sd.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

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
    public ResponseEntity<ArticleEntity> createArticleEntity(@RequestBody ArticleRequest article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDateTime now = LocalDateTime.now();
        ArticleEntity articleEntity = new ArticleEntity(username, article.getTitle(), article.getDescription(), article.getCompany(), article.getDate(),article.isAi(), article.isPublic(), article.getCategory(), now, now);
        System.out.println("Sending "+ articleEntity);
        articleRepository.save(articleEntity);
        System.out.println("Saved article");
        return ResponseEntity.ok(articleEntity);
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
            List<ArticleEntity> articles = articleRepository.findAll(ArticlesService.getSpecification(criteria));
            return ResponseEntity.ok(articles);

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
            @RequestParam(required = false) Boolean isPublic,
            @RequestParam(required = false) Boolean isAi,
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

            SearchArticleCriteria criteria = new SearchArticleCriteria(
                    id,
                    title,
                    company,
                    null,
                    isAi,
                    date,
                    username,
                    category,
                    parsedCreatedAt);

            List<ArticleEntity> articles = articleRepository.findAll(ArticlesService.getSpecification(criteria));
            return ResponseEntity.ok(articles);

        } catch (Exception e) {
            System.out.println("Error while fetching articles by user: {}"+ e.getMessage()+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user articles.");
        }
    }




    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> updateArticleEntity(@PathVariable Long id, @RequestBody ArticleEntity articleDetails) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(articleDetails.getTitle());
                    article.setDescription(articleDetails.getDescription());
                    article.setCompany(articleDetails.getCompany());
                    article.setTimeUnit(articleDetails.getTimeUnit());
                    return ResponseEntity.ok(articleRepository.save(article));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteArticleEntity(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    articleRepository.delete(article);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
