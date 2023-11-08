package com.john.springbootmall.service.impl;

import com.john.springbootmall.dao.UserDao;
import com.john.springbootmall.dto.UserLoginRequest;
import com.john.springbootmall.dto.UserRegisterRequest;
import com.john.springbootmall.model.User;
import com.john.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查是否重複註冊帳號

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user!=null){
            log.warn("使用者Email: {}, 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 成功註冊帳號
        else {
            return userDao.register(userRegisterRequest);
        }
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if (user==null){
           log.warn("此帳號尚未註冊: {}", userLoginRequest.getEmail());
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(userLoginRequest.getPassword().equals(user.getPassword())){
            return user;
        }
        else{
            log.warn("密碼錯誤，請再試一次");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
