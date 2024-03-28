package com.orderapp.foodorder.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.OrderMongo;

public interface OrderMongoRepository extends MongoRepository<OrderMongo, Long> {

}
