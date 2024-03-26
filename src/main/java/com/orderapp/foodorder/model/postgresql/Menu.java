package com.orderapp.foodorder.model.postgresql;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Menu {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column
    private String name;

    @Column
    private Short rating;

    @Column
    private int harga;

    @Column
    private Short level;

    @ManyToOne
    @JoinColumn(name = "resto_id")
    private Restaurant resto;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", length = 29)
    private Timestamp createdTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", length = 29)
    private Timestamp modifiedTime;
}
