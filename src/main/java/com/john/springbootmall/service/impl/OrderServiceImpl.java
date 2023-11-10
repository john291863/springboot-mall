package com.john.springbootmall.service.impl;

import com.john.springbootmall.dao.OrderDao;
import com.john.springbootmall.dao.ProductDao;
import com.john.springbootmall.dto.CreateOrderRequest;
import com.john.springbootmall.model.Order;
import com.john.springbootmall.model.OrderItem;
import com.john.springbootmall.model.Product;
import com.john.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, List<CreateOrderRequest> list) {
        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0 ;

        for (CreateOrderRequest createOrderRequest : list) {
            Product product = productDao.getProductById(createOrderRequest.getProductId());
            int amount = product.getPrice() * createOrderRequest.getQuantity();
            totalAmount += amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(createOrderRequest.getProductId());
            orderItem.setQuantity(createOrderRequest.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItem(orderId, orderItemList);
        return orderId;
    }
}
