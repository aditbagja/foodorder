package com.orderapp.foodorder.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.OrderMongo;

public interface OrderMongoRepository extends MongoRepository<OrderMongo, Long> {
    List<OrderMongo> findAllByCustomer_Id(Long userId);
}
