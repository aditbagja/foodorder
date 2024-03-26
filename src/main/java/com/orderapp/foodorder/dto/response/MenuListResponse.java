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
public class MenuListResponse {
    private RestoInfo resto;
    private List<Menus> menus;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestoInfo {
        private Long restoId;
        private String restoName;
        private String alamat;
        private String timeOpen;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Menus {
        private Long menuId;
        private String menuName;
        private int rating;
        private int level;
        private int harga;
    }
}
