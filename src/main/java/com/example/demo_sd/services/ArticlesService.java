package com.example.demo_sd.services;

import com.example.demo_sd.dto.SearchArticleCriteria;
import com.example.demo_sd.entities.ArticleEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticlesService {
    static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    public static Specification<ArticleEntity> getSpecification(SearchArticleCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getId() != null) {
                predicates.add(cb.equal(root.get("id"), criteria.getId()));
            }
            if (!isNullOrEmpty(criteria.getTitle())) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
            }
            if (!isNullOrEmpty(criteria.getCompany())) {
                predicates.add(cb.equal(root.get("company"), criteria.getCompany()));
            }
            if (criteria.getIsPublic() != null) {
                predicates.add(cb.equal(root.get("isPublic"), criteria.getIsPublic()));
            }
            if (criteria.getIsAi() != null) {
                predicates.add(cb.equal(root.get("isAi"), criteria.getIsAi()));
            }
            if (!isNullOrEmpty(criteria.getTimeUnit())) {
                predicates.add(cb.equal(root.get("timeUnit"), criteria.getDate()));
            }
            if (!isNullOrEmpty(criteria.getUser())) {
                predicates.add(cb.equal(root.get("author"), criteria.getUser()));
            }
            if (!isNullOrEmpty(criteria.getCategory())) {
                predicates.add(cb.equal(root.get("category"), criteria.getCategory()));
            }
            if (criteria.getCreatedAt() != null) {
                predicates.add(cb.equal(root.get("createdAt"), criteria.getCreatedAt()));
            }
            if (!isNullOrEmpty(criteria.getDate())) {
                LocalDate now = LocalDate.now();
                LocalDate startDate = null;
                LocalDate endDate = null;

                switch (criteria.getDate()) {
                    case "Last Week":
                        startDate = now.minus(1, ChronoUnit.WEEKS).with(java.time.DayOfWeek.MONDAY);
                        endDate = startDate.plusDays(6);
                        break;
                    case "Last Month":
                        startDate = now.minus(1, ChronoUnit.MONTHS).withDayOfMonth(1);
                        endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                        break;
                    case "Last Year":
                        startDate = now.minus(1, ChronoUnit.YEARS).withDayOfYear(1);
                        endDate = startDate.withDayOfYear(startDate.lengthOfYear());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid date value: " + criteria.getDate());
                }

                if (startDate != null && endDate != null) {
                    predicates.add(cb.between(root.get("createdAt"), startDate, endDate));
                }
            }


            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}