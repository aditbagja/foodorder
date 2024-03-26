package com.orderapp.foodorder.model.mongoDb;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "database_sequence")
public class DatabaseSequence {
    @Id
    private String id;

    private long seq;
}
