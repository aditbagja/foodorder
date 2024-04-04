package com.orderapp.foodorder.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate orderDate;
}
