package com.john.springbootmall.service;

import com.john.springbootmall.dto.CreateOrderRequest;
import com.john.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, List<CreateOrderRequest> list);
}
