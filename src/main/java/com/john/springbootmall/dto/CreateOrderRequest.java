package com.john.springbootmall.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
