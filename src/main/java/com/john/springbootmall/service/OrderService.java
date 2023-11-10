package com.john.springbootmall.service;

import com.john.springbootmall.dto.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, List<CreateOrderRequest> list);
}
