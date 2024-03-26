package com.orderapp.foodorder.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.RestaurantMongo;

public interface RestaurantMongoRepository extends MongoRepository<RestaurantMongo, Long> {

}
