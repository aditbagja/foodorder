package com.orderapp.foodorder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticResponse {
    private Long totalOrders;
    private OrderByStatus orderByStatus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderByStatus {
        private Long ongoing;
        private Long completed;
        private Long cancelled;
    }
}
