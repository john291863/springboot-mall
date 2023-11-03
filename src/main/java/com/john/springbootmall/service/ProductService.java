package com.john.springbootmall.service;


import com.john.springbootmall.constant.ProductCategory;
import com.john.springbootmall.dto.ProductQueryParams;
import com.john.springbootmall.dto.ProductRequest;
import com.john.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductInfo(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);


}
