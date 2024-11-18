package com.example.demo_sd.repositories;

import com.example.demo_sd.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByIsPublicTrue();

    List<ArticleEntity> findByIsPublicFalse();

    List<ArticleEntity> findByIsAiTrue();

    List<ArticleEntity> findByIsAiFalse();

    List<ArticleEntity> findByCreatedAtAfter(LocalDateTime timestamp);

    List<ArticleEntity> findByCreatedAtBefore(LocalDateTime timestamp);

    List<ArticleEntity> findByCompany(String company);

    List<ArticleEntity> findByTitleContainingIgnoreCase(String keyword);

    List<ArticleEntity> findByCategory(String category);

    List<ArticleEntity> findByCompanyAndIsPublicTrue(String company);

    List<ArticleEntity> findByIsAiTrueAndCreatedAtAfter(LocalDateTime timestamp);

    List<ArticleEntity> findByCategoryAndTitleContainingIgnoreCase(String category, String keyword);
}
