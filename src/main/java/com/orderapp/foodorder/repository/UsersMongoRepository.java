package com.orderapp.foodorder.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.UsersMongo;

public interface UsersMongoRepository extends MongoRepository<UsersMongo, Long> {
    UsersMongo findByUsername(String username);
}
