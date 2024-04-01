package com.orderapp.foodorder.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
import com.orderapp.foodorder.exception.classes.DataNotFoundException;
import com.orderapp.foodorder.model.mongoDb.CartMongo;
import com.orderapp.foodorder.model.mongoDb.OrderMongo;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;
import com.orderapp.foodorder.model.mongoDb.OrderMongo.Customer;
import com.orderapp.foodorder.model.mongoDb.OrderMongo.OrderDetail;
import com.orderapp.foodorder.repository.CartMongoRespository;
import com.orderapp.foodorder.repository.OrderMongoRepository;
import com.orderapp.foodorder.repository.UsersMongoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMongoRepository orderMongoRepository;

    @Autowired
    private UsersMongoRepository usersMongoRepository;

    @Autowired
    private CartMongoRespository cartMongoRespository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final HttpStatus statusOk = HttpStatus.OK;

    @Transactional
    public ResponseEntity<Object> createOrder(Long userId) {
        UsersMongo userInfo = usersMongoRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User tidak ditemukan"));
        CartMongo cartInfo = cartMongoRespository.findAllByUser_Id(userId)
                .orElseThrow(() -> new DataNotFoundException("Cart tidak ditemukan"));

        int quantity = cartInfo.getMenus().stream().mapToInt(c -> c.getQuantity()).sum();
        OrderMongo newOrder = OrderMongo.builder()
                .id(sequenceGeneratorService.generateSequence(OrderMongo.SEQUENCE_NAME))
                .customer(new Customer(userInfo.getId(), userInfo.getUsername(), userInfo.getFullname(),
                        userInfo.getAlamat()))
                .orderDate(Timestamp.valueOf(LocalDateTime.now()))
                .total(cartInfo.getTotalHarga())
                .quantity(quantity)
                .status("Ongoing")
                .detail(OrderDetail.builder()
                        .resto(cartInfo.getResto())
                        .menu(cartInfo.getMenus())
                        .build())
                .build();

        orderMongoRepository.save(newOrder);
        cartMongoRespository.delete(cartInfo);
        kafkaProducerService.sendOrderData(newOrder);

        String message = "Berhasil melakukan order user " + userInfo.getFullname();

        ResponseBodyDTO response = ResponseBodyDTO.builder()
                .total(cartInfo.getMenus().size())
                .data(newOrder)
                .message(message)
                .code(statusOk.value())
                .status(statusOk.getReasonPhrase())
                .build();

        log.info(message);

        return new ResponseEntity<>(response, statusOk);
    }
}
