package com.orderapp.foodorder.dto.response;

import java.util.List;

import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoricalResponse {
    private Long orderId;
    private RestoInfo resto;
    private List<String> menus;
    private CustomerInfo customer;
    private String status;
    private int totalHarga;
    private int quantity;
    private String orderDate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerInfo {
        private Long customerId;
        private String fullname;
        private String alamat;
    }
}
