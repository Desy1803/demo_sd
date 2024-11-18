package com.example.demo_sd.entities;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
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

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean AI) {
        isAi = AI;
    }

    @Column(name="is_ai")
    private boolean isAi;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime createdAt) {
        this.updatedAt = updatedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



}
