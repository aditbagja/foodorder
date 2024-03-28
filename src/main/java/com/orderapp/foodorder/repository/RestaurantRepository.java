package com.orderapp.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.foodorder.model.postgresql.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
