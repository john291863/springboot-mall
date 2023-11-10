package com.john.springbootmall.controller;

import com.john.springbootmall.dto.CreateOrderRequest;
import com.john.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid List<CreateOrderRequest> list){
        System.out.println(list);
        Integer orderId = orderService.createOrder(userId, list);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
