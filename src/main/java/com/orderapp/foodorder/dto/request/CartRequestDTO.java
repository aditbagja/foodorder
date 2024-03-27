package com.orderapp.foodorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDTO {
    private Long userId;
    private Long restoId;
    private Long menuId;
}
