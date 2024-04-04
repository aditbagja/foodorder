package com.orderapp.foodorder.dto.response;

import java.util.List;

import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;
import com.orderapp.foodorder.dto.response.OrderHistoricalResponse.CustomerInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartListResponse {
    private Long cartId;
    private CustomerInfo customer;
    private RestoInfo resto;
    private List<MenusCartInfoDTO> menus;
    private Integer totalHarga;
    private int totalMakanan;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenusCartInfoDTO {
        private Long menuId;
        private String menuName;
        private int level;
        private int harga;
        private int quantity;
    }
}
