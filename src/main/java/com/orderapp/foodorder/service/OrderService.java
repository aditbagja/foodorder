package com.orderapp.foodorder.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.request.OrderActionDTO;
import com.orderapp.foodorder.dto.response.MessageResponse;
import com.orderapp.foodorder.dto.response.OrderListResponse;
import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;
import com.orderapp.foodorder.exception.classes.BadRequestException;
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
        private static final String orderSuccessMessage = "Berhasil memuat data Order";

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
                                .orderDate(Timestamp.valueOf(LocalDateTime.now()).toString())
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

        public ResponseEntity<Object> updateOrder(OrderActionDTO request) {
                OrderMongo orderMongo = orderMongoRepository.findById(request.getOrderId())
                                .orElseThrow(() -> new DataNotFoundException("Data Order tidak ditemukan"));
                log.info(orderMongo.getStatus());
                if (orderMongo.getStatus().equals("Ongoing")) {
                        orderMongo.setStatus(request.getAction());
                        orderMongoRepository.save(orderMongo);
                        kafkaProducerService.sendOrderData(orderMongo);

                        log.info("Data orders = " + orderMongo);

                        String message = "Berhasil mengubah status Order (" + request.getAction()
                                        + ") untuk Order user: "
                                        + orderMongo.getCustomer().getFullname();

                        MessageResponse response = MessageResponse.builder()
                                        .message(message)
                                        .code(statusOk.value())
                                        .status(statusOk.getReasonPhrase())
                                        .build();

                        log.info(message);
                        return new ResponseEntity<>(response, statusOk);
                } else {
                        throw new BadRequestException("Anda tidak bisa mengubah Order yang sudah selesai");
                }
        }

        public ResponseEntity<Object> getUserOrder(Long userId) {
                List<OrderMongo> orderList = orderMongoRepository.findAllByCustomer_Id(userId);
                if (orderList.isEmpty()) {
                        throw new DataNotFoundException("Data Order tidak ditemukan");
                } else {
                        List<OrderListResponse> datas = orderList.stream().map(data -> new OrderListResponse(
                                        data.getId(),
                                        new RestoInfo(data.getDetail().getResto().getId(),
                                                        data.getDetail().getResto().getName(),
                                                        data.getDetail().getResto().getAlamat(),
                                                        data.getDetail().getResto().getTimeOpen()),
                                        data.getStatus(), data.getTotal(), data.getQuantity(),
                                        Timestamp.valueOf(data.getOrderDate()))).toList();

                        ResponseBodyDTO response = ResponseBodyDTO.builder()
                                        .total(orderList.size())
                                        .data(datas)
                                        .message(orderSuccessMessage)
                                        .code(statusOk.value())
                                        .status(statusOk.getReasonPhrase())
                                        .build();

                        log.info(orderSuccessMessage);

                        return new ResponseEntity<>(response, statusOk);
                }
        }

        public ResponseEntity<Object> getOrderById(Long orderId) {
                OrderMongo orderData = orderMongoRepository.findById(orderId)
                                .orElseThrow(() -> new DataNotFoundException("Data Order tidak ditemukan"));

                ResponseBodyDTO response = ResponseBodyDTO.builder()
                                .total(1)
                                .data(orderData)
                                .message(orderSuccessMessage)
                                .code(statusOk.value())
                                .status(statusOk.getReasonPhrase())
                                .build();

                log.info(orderSuccessMessage);

                return new ResponseEntity<>(response, statusOk);
        }
}
