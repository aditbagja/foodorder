package com.orderapp.foodorder.dto.response;

import java.sql.Timestamp;

import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponse {
    private Long orderId;
    private RestoInfo resto;
    private String status;
    private int totalHarga;
    private int quantity;
    private Timestamp orderDate;
}
