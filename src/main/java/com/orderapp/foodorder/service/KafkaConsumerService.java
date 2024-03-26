package com.orderapp.foodorder.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;
import com.orderapp.foodorder.model.postgresql.Users;
import com.orderapp.foodorder.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private UsersRepository usersRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "user")
    public void consumeDataUser(String users) {
        log.info("userData is = " + users);

        ObjectMapper mapper = new ObjectMapper();
        try {
            UsersMongo usersMongo = mapper.readValue(users, UsersMongo.class);
            Users newUsers = Users.builder()
                    .userId(usersMongo.getId())
                    .username(usersMongo.getUsername())
                    .fullname(usersMongo.getFullname())
                    .password(usersMongo.getPassword())
                    .alamat(usersMongo.getAlamat())
                    .createdTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();

            usersRepository.save(newUsers);

            log.info("Berhasil save data user ke PostgreSQL");
        } catch (JsonProcessingException e) {
            log.error("Error Getting User Data: ", e);
        }
    }
}
