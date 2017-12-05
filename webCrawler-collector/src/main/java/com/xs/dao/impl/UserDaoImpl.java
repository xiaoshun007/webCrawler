package com.xs.dao.impl;

import com.xs.dao.IUserDao;
import com.xs.domain.User;
import com.xs.mapper.TestMapper;
import com.xs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDaoImpl implements IUserDao {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private TestMapper testMapper;

    @Override
    public User selectUserById(Integer userId) {
        return null;
    }
}
