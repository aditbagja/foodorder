package com.orderapp.foodorder.model.mongoDb;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class OrderMongo {
    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private Long id;

    private Customer customer;

    @Field("order_date")
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Field("total_harga")
    private int total;

    private Integer quantity;
    private String status;
    private OrderDetail detail;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String username;
        private String fullname;
        private String alamat;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetail {
        private List<MenuMongo> menu;
    }
}
