package com.john.springbootmall.dto;

import com.john.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {
    private String search;
    private ProductCategory productCategory;
    private String orderBy;
    private String sort;
}
