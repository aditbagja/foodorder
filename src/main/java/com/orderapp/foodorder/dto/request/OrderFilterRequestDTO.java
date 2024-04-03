package com.orderapp.foodorder.dto.request;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilterRequestDTO {
    private String customerName;
    private String restoName;
    private String menuName;
    private String status;
    private Date orderDate;
}
