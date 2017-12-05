package com.xs.dao.impl;

import com.xs.dao.IUserDao;
import com.xs.domain.User;
import com.xs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements IUserDao {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserById(Integer userId) {
        return null;
    }
}
