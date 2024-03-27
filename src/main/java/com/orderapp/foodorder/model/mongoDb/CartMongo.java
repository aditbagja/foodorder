package com.orderapp.foodorder.model.mongoDb;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class CartMongo {
    @Transient
    public static final String SEQUENCE_NAME = "cart_sequence";

    @Id
    private Long id;

    private UsersMongo user;
    private RestaurantMongo resto;
    private List<MenusCartInfo> menus;
    private Integer totalHarga;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenusCartInfo {
        private Long id;
        private String menuName;
        private int rating;
        private int level;
        private int harga;
        private int quantity;
    }
}
