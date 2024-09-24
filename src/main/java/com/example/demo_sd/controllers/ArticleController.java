package com.example.demo_sd.controllers;

import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.entities.UserEntity;
import com.example.demo_sd.repositories.ArticleRepository;
import com.example.demo_sd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository; // Repository per gestire gli utenti


    @PostMapping
    public ResponseEntity<ArticleEntity> createArticleEntity(@RequestBody ArticleEntity article) {
        UserEntity author = userRepository.findById(article.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        article.setAuthor(author);
        ArticleEntity savedArticleEntity = articleRepository.save(article);
        return ResponseEntity.ok(savedArticleEntity);
    }

    @GetMapping
    public List<ArticleEntity> getAllArticleEntitys() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleEntity> getArticleEntityById(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Object> deleteArticleEntity(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    articleRepository.delete(article);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
