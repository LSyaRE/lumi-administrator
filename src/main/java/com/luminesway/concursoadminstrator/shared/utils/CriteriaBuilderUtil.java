package com.luminesway.concursoadminstrator.shared.utils;

import com.luminesway.concursoadminstrator.shared.dtos.FilterDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class to build dynamic JPA Specifications based on a list of filters.
 * This allows creating flexible queries with various operators (eq, ne, like, in, etc.).
 */
@Component
public class CriteriaBuilderUtil {

    /**
     * Builds a JPA Specification for the given entity class based on a list of filters.
     *
     * @param entityClass The entity class to filter.
     * @param filters     List of FilterDTO objects representing field, operator, and value.
     * @param <T>         The type of the entity.
     * @return A Specification object that can be used in JPA repository queries.
     */
    public static <T> Specification<T> buildSpecification(
            Class<T> entityClass,
            List<FilterDTO> filters) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Iterate through all filters and convert them into predicates
            for (FilterDTO filter : filters) {
                try {
                    Path<Object> fieldPath = getFieldPath(root, filter.getField());
                    Predicate predicate = createPredicate(cb, fieldPath, filter);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "Error processing filter: " + filter.getField(), e);
                }
            }

            // Combine all predicates with AND, or return a true condition if none exist
            return predicates.isEmpty() ?
                    cb.conjunction() :
                    cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Resolves a nested field path from a root entity.
     * Supports dot notation for nested fields, e.g., "user.address.city".
     *
     * @param root      Root entity in the Criteria query.
     * @param fieldName Field name, possibly nested with dot notation.
     * @return Path object representing the field.
     */
    private static Path<Object> getFieldPath(Root<?> root, String fieldName) {
        String[] fields = fieldName.split("\\.");
        Path<Object> path = root.get(fields[0]);

        // Traverse nested fields if present
        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }

        return path;
    }

    /**
     * Creates a JPA Predicate for a given field and filter.
     * Supports operators like eq, ne, like, ilike, gt, gte, lt, lte, in, isnull, and isnotnull.
     *
     * @param cb        CriteriaBuilder used to create predicates.
     * @param fieldPath Path to the field in the entity.
     * @param filter    FilterDTO containing field, operator, and value.
     * @return Predicate object for the filter.
     * @throws IllegalArgumentException if the operator is not supported.
     */
    @SuppressWarnings("unchecked")
    private static Predicate createPredicate(CriteriaBuilder cb,
                                             Path<Object> fieldPath,
                                             FilterDTO filter) {
        Object value = filter.getValue();
        String operator = filter.getOperator().toLowerCase();

        switch (operator) {
            case "eq":
                return cb.equal(fieldPath, value);
            case "ne":
                return cb.notEqual(fieldPath, value);
            case "like":
                return cb.like(fieldPath.as(String.class), "%" + value + "%");
            case "ilike":
                return cb.like(cb.lower(fieldPath.as(String.class)),
                        ("%" + value + "%").toLowerCase());
            case "gt":
                return cb.greaterThan(fieldPath.as(Comparable.class), (Comparable) value);
            case "gte":
                return cb.greaterThanOrEqualTo(fieldPath.as(Comparable.class), (Comparable) value);
            case "lt":
                return cb.lessThan(fieldPath.as(Comparable.class), (Comparable) value);
            case "lte":
                return cb.lessThanOrEqualTo(fieldPath.as(Comparable.class), (Comparable) value);
            case "in":
                return fieldPath.in((Collection<?>) value);
            case "isnull":
                return cb.isNull(fieldPath);
            case "isnotnull":
                return cb.isNotNull(fieldPath);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}