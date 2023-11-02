package com.john.springbootmall.service.impl;

import com.john.springbootmall.dao.ProductDao;
import com.john.springbootmall.model.Product;
import com.john.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductInfo(Integer productId) {
        return productDao.getProductById(productId);
    }
}
