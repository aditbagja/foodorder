package com.orderapp.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.foodorder.model.postgresql.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
