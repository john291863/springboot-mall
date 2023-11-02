package com.john.springbootmall.dao.impl;

import com.john.springbootmall.dao.ProductDao;
import com.john.springbootmall.model.Product;
import com.john.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, " +
                "product_name, category, image_url, " +
                "price, stock, description, created_date, " +
                "last_modified_date from product " +
                "where product_id = :productId";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        List<Product> list = new ArrayList<Product>();
        list=  namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(!list.isEmpty()){
            return list.get(0);
        }
        else{
            return null;
        }
    }
}
