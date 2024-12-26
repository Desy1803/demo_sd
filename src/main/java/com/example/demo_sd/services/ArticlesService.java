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
            if (!isNullOrEmpty(criteria.getDate())) {
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

                switch (criteria.getDate()) {
                    case "Last Week":
                        startDate = now.minus(1, ChronoUnit.WEEKS);
                        break;
                    case "Last Month":
                        startDate = now.minus(1, ChronoUnit.MONTHS);
                        break;
                    case "Last Year":
                        startDate = now.minus(1, ChronoUnit.YEARS);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid date value: " + criteria.getDate());
                }

                if (startDate != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
                }
            }

            // Se non ci sono criteri specifici, restituisci tutti gli articoli (nessun filtro)
            if (predicates.isEmpty()) {
                return cb.conjunction(); // Restituisce una condizione sempre vera
            }

            // Combina i criteri in un AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}