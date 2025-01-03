package com.example.demo_sd.controllers;

import com.example.demo_sd.dto.ArticleDto;
import com.example.demo_sd.dto.SearchArticleCriteria;
import com.example.demo_sd.dto.UploadImage;
import com.example.demo_sd.entities.ArticleEntity;
import com.example.demo_sd.repositories.ArticleRepository;
import com.example.demo_sd.requests.ArticleRequest;
import com.example.demo_sd.services.ArticlesService;
import com.example.demo_sd.services.KeycloakService;
import com.example.demo_sd.utils.ImageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo_sd.utils.ImageParser.decodeImage;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin()
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private KeycloakService keycloakService;


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> createArticleEntity(@RequestBody ArticleRequest article) throws UnsupportedEncodingException {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            byte[] byteImage = null;
            if(article.getImageUrl()!=null)
                byteImage = article.getImageUrl().getBytes("UTF-8");
            LocalDateTime now = LocalDateTime.now();
            ArticleEntity articleEntity = new ArticleEntity(null, article.getTitle(), article.getDescription(), article.getCompany(), username,  byteImage ,article.getTimeUnit(),article.isPublic(), article.getCategory(), article.isAi(),now, now);
            articleRepository.save(articleEntity);
            System.out.println("Saved article");
            return ResponseEntity.ok(articleEntity);
        }catch(Exception e){
            throw new BadRequestException(e);
        }

    }

    @GetMapping("/get-image")
    public ResponseEntity<byte[]> getImage(@RequestParam(required = false) String articleId) {
        System.out.println("Getting image");
        ArticleEntity article = articleRepository.findById(Long.parseLong(articleId)).orElse(null);

        if (article != null && article.getImageUrl() != null) {
            return ResponseEntity.ok(article.getImageUrl());
        } else {
            return null;
        }
    }

    @PostMapping("/set-image")
    public ResponseEntity<ArticleEntity> setImage(@RequestBody() UploadImage data) {
        System.out.println("Setting image " +data.getId());
        byte[] byteImage = data.getImageUrl().getBytes();

        return articleRepository.findById(Long.valueOf(data.getId()))
                .map(article -> {
                    article.setImageUrl(byteImage);
                    return ResponseEntity.ok(articleRepository.save(article));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/public")
    public ResponseEntity<?> getAllPublicArticles(@RequestParam(required = false) Long id,
                                                                @RequestParam(required = false) String title,
                                                                @RequestParam(required = false) String company,
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
            if(category==null || category.equals("All"))
                category=null;
            if(date==null || date.equals("All"))
                date=null;

            SearchArticleCriteria criteria = new SearchArticleCriteria(
                    id != null ? id : null,
                    title != null ? title : null,
                    company != null ? company : null,
                    true,
                    null,
                    date != null ? date : null,
                    null,
                    user != null ? user : null,
                    category != null ? category : null,
                    parsedCreatedAt != null ? parsedCreatedAt : null
            );

            List<ArticleEntity> articlesEntity = articleRepository.findAll(ArticlesService.getSpecification(criteria));

            List<ArticleDto> articlesDto = articlesEntity.stream().map(articleEntity -> {
                ArticleDto dto = new ArticleDto();
                dto.setId(articleEntity.getId());
                dto.setTitle(articleEntity.getTitle());
                dto.setDescription(articleEntity.getDescription());
                dto.setCompany(articleEntity.getCompany());
                dto.setIsPublic(articleEntity.isPublic());
                dto.setIsAi(articleEntity.isAi());
                dto.setDate(articleEntity.getTimeUnit());
                dto.setCategory(articleEntity.getCategory());
                dto.setCreatedAt(articleEntity.getCreatedAt());
                try{
                    dto.setAuthorUsername(keycloakService.getUserInfo(articleEntity.getAuthor()).getUsername());
                    dto.setAuthorEmail(keycloakService.getUserInfo(articleEntity.getAuthor()).getEmail());
                }catch(Exception e){
                    dto.setAuthorUsername("Anonymous");
                    dto.setAuthorEmail("Anonymous");
                }


                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(articlesDto);
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
            @RequestParam(required = false) String date,
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
            if(category==null || category.equals("All"))
                category=null;
            if(date==null || date.equals("All"))
                date=null;
            SearchArticleCriteria criteria = new SearchArticleCriteria(
                    id,
                    title,
                    company,
                    null,
                    null,
                    date,
                    null,
                    username,
                    category,
                    parsedCreatedAt);

            List<ArticleEntity> articlesEntity = articleRepository.findAll(ArticlesService.getSpecification(criteria));

            List<ArticleDto> articlesDto = articlesEntity.stream().map(articleEntity -> {
                ArticleDto dto = new ArticleDto();
                dto.setId(articleEntity.getId());
                dto.setTitle(articleEntity.getTitle());
                dto.setDescription(articleEntity.getDescription());
                dto.setCompany(articleEntity.getCompany());
                dto.setIsPublic(articleEntity.isPublic());
                dto.setIsAi(articleEntity.isAi());
                dto.setDate(articleEntity.getTimeUnit());
                dto.setCategory(articleEntity.getCategory());
                dto.setCreatedAt(articleEntity.getCreatedAt());
                try{
                    dto.setAuthorUsername(keycloakService.getUserInfo(articleEntity.getAuthor()).getUsername());
                    dto.setAuthorEmail(keycloakService.getUserInfo(articleEntity.getAuthor()).getEmail());
                }catch(Exception e){
                    dto.setAuthorUsername("Anonymous");
                    dto.setAuthorEmail("Anonymous");
                }


                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(articlesDto);
        } catch (Exception e) {
            System.out.println("Error while fetching articles by user: {}"+ e.getMessage()+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching user articles.");
        }
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ArticleEntity> updateArticleEntity(
            @RequestParam(required = true) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String isPublic,
            @RequestParam(required = false) String isAi,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String category
    ) {
        System.out.println("Update article ");

        boolean isPublic1 = isPublic != null ? Boolean.parseBoolean(isPublic) : false;
        boolean isAi1 = isAi != null ? Boolean.parseBoolean(isAi) : false;

        return articleRepository.findById(Long.valueOf(id))
                .map(article -> {
                    if (title != null && !title.isEmpty()) {
                        article.setTitle(title);
                    }

                    if (description != null && !description.isEmpty()) {
                        article.setDescription(description);
                    }

                    if (company != null && !company.isEmpty()) {
                        article.setCompany(company);
                    }

                    if (date != null && !date.isEmpty()) {
                        article.setTimeUnit(date);
                    }

                    if (isPublic != null) {
                        article.setPublic(isPublic1);
                    }

                    if (isAi != null) {
                        article.setAi(isAi1);
                    }

                    if (category != null && !category.isEmpty()) {
                        article.setCategory(category);
                    }

                    return ResponseEntity.ok(articleRepository.save(article));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteArticleEntity(@RequestParam Long id) {
        System.out.println("Deleted article "+id);
        return articleRepository.findById(id)
                .map(article -> {
                    articleRepository.delete(article);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
