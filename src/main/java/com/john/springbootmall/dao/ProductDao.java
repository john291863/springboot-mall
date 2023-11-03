package com.john.springbootmall.dao;

import com.john.springbootmall.dto.ProductRequest;
import com.john.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts();
    Product getProductById(Integer productId );
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);

}
