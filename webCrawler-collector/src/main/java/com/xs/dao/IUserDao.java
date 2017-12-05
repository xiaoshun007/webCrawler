package com.xs.dao;

import com.xs.domain.User;

public interface IUserDao {

    /**
     * @param userId
     * @return User
     */
    public User selectUserById(Integer userId);

}
