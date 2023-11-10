package com.john.springbootmall.service.impl;

import com.john.springbootmall.dao.OrderDao;
import com.john.springbootmall.dao.ProductDao;
import com.john.springbootmall.dao.UserDao;
import com.john.springbootmall.dto.CreateOrderRequest;
import com.john.springbootmall.model.Order;
import com.john.springbootmall.model.OrderItem;
import com.john.springbootmall.model.Product;
import com.john.springbootmall.model.User;
import com.john.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

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
        //當創建一筆訂單時，應要確認是否有這位user，所以要判斷!
        User user = userDao.getUserById(userId);

        if(user == null){
            log.warn("此使用者{}，非會員", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0 ;

        for (CreateOrderRequest createOrderRequest : list) {
            Product product = productDao.getProductById(createOrderRequest.getProductId());

            if(product == null){
                log.warn("此商品不存在: {}", createOrderRequest.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            else if (createOrderRequest.getQuantity() > product.getStock() ||
                     createOrderRequest.getQuantity() < 0){
                log.warn("商品庫存不足: {}", product.getProductName() );
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateProductStock(product.getProductId(), product.getStock() - createOrderRequest.getQuantity());


            // 計算總價
            int amount = product.getPrice() * createOrderRequest.getQuantity();
            totalAmount += amount;

            //CreateOrderRequest 轉換成 OrderItem
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
