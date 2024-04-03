package com.orderapp.foodorder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.orderapp.foodorder.dto.request.OrderFilterRequestDTO;
import com.orderapp.foodorder.model.postgresql.Order;

import jakarta.persistence.criteria.Predicate;

public class OrderSpecification {
    public static Specification<Order> filterOrder(OrderFilterRequestDTO request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getCustomerName() != null) {
                String searchValue = "%" + request.getCustomerName() + "%";
                Predicate customerPredicate = criteriaBuilder.like(root.get("user").get("fullname"), searchValue);
                predicates.add(customerPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}