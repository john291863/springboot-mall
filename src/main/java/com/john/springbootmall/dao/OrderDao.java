package com.john.springbootmall.dao;

import com.john.springbootmall.model.Order;
import com.john.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    List<OrderItem> getOrderItemByOrderId(Integer orderId);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
}
