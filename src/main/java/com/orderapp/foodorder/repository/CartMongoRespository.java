package com.orderapp.foodorder.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.CartMongo;
import com.orderapp.foodorder.model.mongoDb.RestaurantMongo;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;

public interface CartMongoRespository extends MongoRepository<CartMongo, Long> {
    Optional<CartMongo> findByUserAndResto(UsersMongo user, RestaurantMongo resto);
}
