package com.xs.dao;

import com.xs.domain.User;

public interface UserDao {

    /**
     * @param userId
     * @return User
     */
    public User selectUserById(Integer userId);

}
