package com.orderapp.foodorder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBodyDTO {
    private int total;
    private Object data;
    private String message;
    private int code;
    private String status;
}
