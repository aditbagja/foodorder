package com.orderapp.foodorder.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.orderapp.foodorder.model.mongoDb.OrderMongo;

public interface OrderMongoRepository extends MongoRepository<OrderMongo, Long> {
    List<OrderMongo> findAllByCustomer_Id(Long userId);

    @Query("{ $and: [{ 'status': 'Ongoing' }, { 'customer.id': ?0 }] }")
    List<OrderMongo> findOngoingOrderByCustomerId(Long customerId);
}
