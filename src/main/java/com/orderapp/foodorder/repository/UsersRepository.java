package com.orderapp.foodorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.foodorder.model.postgresql.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
