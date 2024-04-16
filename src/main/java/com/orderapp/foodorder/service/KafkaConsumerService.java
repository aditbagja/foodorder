package com.orderapp.foodorder.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderapp.foodorder.model.mongoDb.OrderMongo;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;
import com.orderapp.foodorder.model.mongoDb.CartMongo.MenusCartInfo;
import com.orderapp.foodorder.model.postgresql.Orders;
import com.orderapp.foodorder.model.postgresql.Restaurant;
import com.orderapp.foodorder.model.postgresql.Users;
import com.orderapp.foodorder.repository.OrderRepository;
import com.orderapp.foodorder.repository.RestaurantRepository;
import com.orderapp.foodorder.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @KafkaListener(topics = "${spring.kafka.topic.user}", groupId = "user")
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

            log.info("Berhasil save data user ke OLAP PostgreSQL");
        } catch (JsonProcessingException e) {
            log.error("Error Getting User Data: ", e);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "order")
    public void consumeDataOrder(String orderData) {
        log.info("orderData is = " + orderData);

        ObjectMapper mapper = new ObjectMapper();
        try {
            OrderMongo ordersMongo = mapper.readValue(orderData, OrderMongo.class);
            Optional<Orders> orderExistData = orderRepository.findById(ordersMongo.getId());

            if (orderExistData.isEmpty()) {
                Optional<Users> users = usersRepository.findById(ordersMongo.getCustomer().getId());
                Optional<Restaurant> resto = restaurantRepository.findById(ordersMongo.getDetail().getResto().getId());

                List<String> listMenu = ordersMongo.getDetail().getMenu().stream().map(MenusCartInfo::getMenuName)
                        .toList();

                Orders newOrder = Orders.builder()
                        .orderId(ordersMongo.getId())
                        .user(users.get())
                        .resto(resto.get())
                        .menuName(listMenu)
                        .orderDate(Timestamp.valueOf(ordersMongo.getOrderDate()))
                        .quantity(ordersMongo.getQuantity())
                        .totalHarga(ordersMongo.getTotal())
                        .status(ordersMongo.getStatus())
                        .createdTime(Timestamp.valueOf(ordersMongo.getOrderDate()))
                        .build();

                orderRepository.save(newOrder);

                log.info("Berhasil save data order ke OLAP PostgreSQL");
            } else {
                log.info("Order data from OLAP PostgreSQL " + orderExistData);
                orderExistData.get().setStatus(ordersMongo.getStatus());
                orderExistData.get().setModifiedTime(Timestamp.valueOf(LocalDateTime.now()));
                orderRepository.save(orderExistData.get());

                log.info("Berhasil mengubah status (" + ordersMongo.getStatus() + ") OLAP PostgreSQL");
            }
        } catch (JsonProcessingException e) {
            log.error("Error Getting User Data: ", e);
        }
    }

}
