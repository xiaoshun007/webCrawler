package com.xs.service;

import com.xs.domain.User;

public interface IUserService {
    User selectUserById(Integer userId);
}
