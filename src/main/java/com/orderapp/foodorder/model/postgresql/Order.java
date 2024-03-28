package com.orderapp.foodorder.model.postgresql;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"order\"")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_order_order_id_seq")
    @SequenceGenerator(name = "generator_order_order_id_seq", sequenceName = "order_order_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "order_id", unique = true, nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "resto_id")
    private Restaurant resto;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "order_date")
    private Timestamp orderDate;

    @Column
    private int quantity;

    @Column(name = "total_harga")
    private int totalHarga;

    @Column
    private String status;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", length = 29)
    private Timestamp createdTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", length = 29)
    private Timestamp modifiedTime;
}
