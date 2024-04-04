package com.orderapp.foodorder.service;

import java.time.LocalDate;
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

            if (request.getRestoName() != null) {
                String searchValue = "%" + request.getRestoName() + "%";
                Predicate restoPredicate = criteriaBuilder.like(root.get("resto").get("name"), searchValue);
                predicates.add(restoPredicate);
            }

            if (request.getMenuName() != null) {
                String searchValue = "%" + request.getMenuName() + "%";
                Predicate menuPredicate = criteriaBuilder.like(root.get("menu").get("name"), searchValue);
                predicates.add(menuPredicate);
            }

            if (request.getStatus() != null) {
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), request.getStatus());
                predicates.add(statusPredicate);
            }

            if (request.getOrderDate() != null) {
                Predicate requestDatePredicate = criteriaBuilder.equal(
                        criteriaBuilder.function("date", LocalDate.class, root.get("orderDate")),
                        request.getOrderDate());
                predicates.add(requestDatePredicate);

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
