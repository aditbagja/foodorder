package com.orderapp.foodorder.dto.response;

import java.util.List;

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
    private List<MenusCartInfoDTO> menus;
    private Integer totalHarga;

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
