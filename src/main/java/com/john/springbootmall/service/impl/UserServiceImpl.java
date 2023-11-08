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
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

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
        if (user != null) {
            log.warn("使用者Email: {}, 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5 生成密碼雜湊值

        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 成功註冊帳號

        return userDao.register(userRegisterRequest);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        //檢查user是否存在
        if (user == null) {
            log.warn("此帳號尚未註冊: {}", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5 生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //比較密碼
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        else {
            log.warn("密碼錯誤，請再試一次");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
