package com.john.springbootmall.dto;

import com.john.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class OrderQueryParams {
    private Integer userId;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
