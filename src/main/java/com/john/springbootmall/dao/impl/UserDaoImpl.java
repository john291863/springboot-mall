package com.john.springbootmall.dao.impl;

import com.john.springbootmall.dao.UserDao;
import com.john.springbootmall.dto.UserRegisterRequest;
import com.john.springbootmall.model.User;
import com.john.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        String sql = "Insert into user (email, password, created_date, last_modified_date ) " +
                "VALUES(:email, :password, :createDate, :lastModifiedDate);";
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date, " +
                "last_modified_date FROM USER WHERE user_id= :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if(list.isEmpty()){
            return null;
        }
        else{
            return list.get(0);
        }
    }

    @Override
    public User getUserByEmail(String userEmail) {
        String sql = "SELECT user_id, email, password, created_date, " +
                "last_modified_date FROM USER WHERE email = :email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", userEmail);
        List<User> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        if(list.size()>0){
            return list.get(0);
        }
        else{
            return null;
        }
    }
}
