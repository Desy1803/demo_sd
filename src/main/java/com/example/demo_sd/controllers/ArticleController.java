package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.ArticleDto;
import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.entities.UserEntity;
import com.example.demo_sd.repositories.ArticleRepository;
import com.example.demo_sd.repositories.UserRepository;
import com.example.demo_sd.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin()
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KeycloakService keycloakService;


    @PostMapping
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> createArticleEntity(@RequestBody ArticleDto article, Principal principal) {
        String username = "lala"; //TODO
        ArticleEntity articleEntity = new ArticleEntity(article.getTitle(), article.getDescription(), article.getCompany(), article.getTimeUnit());
        articleEntity.setAuthor(username);
        articleRepository.save(articleEntity);
        return ResponseEntity.ok(articleEntity);
    }

    @GetMapping
    //@PreAuthorize("isAuthenticated()")
    public List<ArticleEntity> getAllArticleEntities() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> getArticleEntityById(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    //@PreAuthorize("isAuthenticated()")
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
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteArticleEntity(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    articleRepository.delete(article);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
