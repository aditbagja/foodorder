package com.orderapp.foodorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
import com.orderapp.foodorder.exception.classes.DataNotFoundException;
import com.orderapp.foodorder.model.mongoDb.RestaurantMongo;
import com.orderapp.foodorder.repository.RestaurantMongoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantService {
    @Autowired
    RestaurantMongoRepository restaurantMongoRepository;

    private static final HttpStatus statusOk = HttpStatus.OK;
    private static final String messageSuccess = "Berhasil memuat data Resto";

    public ResponseEntity<Object> getAllRestaurant() {
        List<RestaurantMongo> resto = restaurantMongoRepository.findAll();
        if (resto.isEmpty()) {
            throw new DataNotFoundException("Data Resto tidak ditemukan");
        }

        ResponseBodyDTO response = ResponseBodyDTO.builder()
                .total(resto.size())
                .data(resto)
                .message(messageSuccess)
                .code(statusOk.value())
                .status(statusOk.getReasonPhrase())
                .build();

        log.info(messageSuccess);

        return new ResponseEntity<>(response, statusOk);
    }
}
