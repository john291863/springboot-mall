package com.john.springbootmall.dao.impl;

import com.john.springbootmall.constant.ProductCategory;
import com.john.springbootmall.dao.ProductDao;
import com.john.springbootmall.dto.ProductQueryParams;
import com.john.springbootmall.dto.ProductRequest;
import com.john.springbootmall.model.Product;
import com.john.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "Select product_id, product_name, category, image_url, " +
                "price, stock, description, " +
                "created_date, last_modified_date from product where 1=1";
        Map<String, Object> map = new HashMap<String, Object>();

        if (productQueryParams.getProductCategory() != null) {
            sql = sql+ " AND category = :category ";
            map.put("category", productQueryParams.getProductCategory().name());
        }

        if(!productQueryParams.getSearch().trim().equals("")){
            sql = sql+ " AND product_name LIKE :search ";
            map.put("search", "%"+productQueryParams.getSearch()+"%");
        }

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

    }

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

    public Integer createProduct(ProductRequest productRequest){
        String sql =
                " Insert into product(product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date) " +
                " VALUES(:productName, :category, :imageUrl, :price, :stock, :description," +
                " :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql =
                "Update product set product_name = :productName, " +
                "category = :category, image_url = :imageUrl, price = :price, " +
                "stock = :stock, description = :description , last_modified_date = :lastModifiedDate " +
                " where product_id = :productId";
        Map<String, Object> map  = new HashMap<String, Object>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("lastModifiedDate", now);
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "Delete  from product where product_id = :productId";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }
}
