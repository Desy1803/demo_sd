package com.example.demo_sd.entities;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "image_url")
    private byte[] imageUrl;

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



}
