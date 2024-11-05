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


    @Column(name = "time_unit")
    private String timeUnit;

    @Column(name = "created_at", nullable = false)
    @Timestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Timestamp
    private LocalDateTime updatedAt;

    public ArticleEntity(String title, String description, String company, String timeUnit) {
        this.title = title;
        this.description = description;
        this.company = company;
        this.timeUnit = timeUnit;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


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
