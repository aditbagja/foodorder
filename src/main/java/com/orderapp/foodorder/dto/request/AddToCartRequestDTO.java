package com.orderapp.foodorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequestDTO {
    private Long userId;
    private Long restoId;
    private Long menuId;
}
