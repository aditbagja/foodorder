package com.orderapp.foodorder.model.mongoDb;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu")
public class MenuMongo {
    @Transient
    public static final String SEQUENCE_NAME = "menu_sequence";

    @Id
    private Long id;

    private RestaurantMongo resto;
    private String name;
    private Integer rating;
    private Integer harga;
    private Integer level;
}
