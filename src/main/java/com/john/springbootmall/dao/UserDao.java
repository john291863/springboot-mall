package com.john.springbootmall.dao;

import com.john.springbootmall.dto.UserRegisterRequest;
import com.john.springbootmall.model.User;

public interface UserDao {
    Integer register(UserRegisterRequest userRegisterRequest);
    User getUserById (Integer userId);
    User getUserByEmail(String userEmail);
}
