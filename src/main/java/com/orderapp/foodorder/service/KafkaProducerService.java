package com.orderapp.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderapp.foodorder.model.mongoDb.OrderMongo;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.user}")
    String kafkaTopicUser;

    @Value("${spring.kafka.topic.order}")
    String kafkaTopicOrder;

    public void sendUserData(UsersMongo users) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(users);
            log.info("Sending User Data...");
            kafkaTemplate.send(kafkaTopicUser, json);
        } catch (JsonProcessingException e) {
            log.error("Error serializing Data object to JSON:", e);
        }
    }

    public void sendOrderData(OrderMongo order) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(order);
            log.info("Sending Order Data...");
            kafkaTemplate.send(kafkaTopicOrder, json);
        } catch (JsonProcessingException e) {
            log.error("Error serializing Data object to JSON:", e);
        }
    }
}
