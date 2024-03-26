package com.orderapp.foodorder.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderapp.foodorder.model.mongoDb.MenuMongo;

public interface MenuMongoRepository extends MongoRepository<MenuMongo, Long> {
    List<MenuMongo> findAllByResto_Id(Long restoId);
}
