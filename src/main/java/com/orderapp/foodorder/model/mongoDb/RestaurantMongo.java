package com.orderapp.foodorder.model.mongoDb;

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
@Document(collection = "restaurant")
public class RestaurantMongo {
    @Transient
    public static final String SEQUENCE_NAME = "restaurant_sequence";

    @Id
    private Long id;

    private String name;
    private String alamat;

    @Field("time_open")
    private String timeOpen;
}
