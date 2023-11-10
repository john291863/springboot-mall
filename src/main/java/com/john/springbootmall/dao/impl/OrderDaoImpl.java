package com.john.springbootmall.dao.impl;

import com.john.springbootmall.dao.OrderDao;
import com.john.springbootmall.model.Order;
import com.john.springbootmall.model.OrderItem;
import com.john.springbootmall.rowmapper.OrderItemRowMapper;
import com.john.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, " +
                "p.product_name, p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p " +
                "ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId ";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);


        return namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT * FROM `order` where order_id = :orderId ";

        Map <String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if(list.size()>0){
            return list.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `ORDER` (user_id, total_amount, created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate) ";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        String sql  = "INSERT INTO ORDER_ITEM (order_id, product_id, quantity, amount) " +
                "VALUES(:orderId, :productId, :quantity, :amount) ";
        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderItemList.size()];
        for(int i = 0; i<orderItemList.size() ;i++){
            OrderItem orderItem = orderItemList.get(i);
            mapSqlParameterSources[i] = new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("orderId", orderId);
            mapSqlParameterSources[i].addValue("productId", orderItem.getProductId());
            mapSqlParameterSources[i].addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSources);
    }
}
