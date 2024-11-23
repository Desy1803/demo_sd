package com.example.demo_sd.entities;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name = "time_unit")
    private String timeUnit;

    @Column(name="is_public")
    private boolean isPublic;

    @Column
    private String category;

    @Column(name="is_ai")
    private boolean isAi;


    @Column(name = "created_at")
    @Timestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Timestamp
    private LocalDateTime updatedAt;

    public ArticleEntity(String author, String title, String description, String company, String timeUnit, boolean isAi, boolean isPublic, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.author=author;
        this.title = title;
        this.description = description;
        this.company = company;
        this.timeUnit = timeUnit;
        this.isAi=isAi;
        this.isPublic=isPublic;
        this.category=category;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }
    public ArticleEntity() {}



}
