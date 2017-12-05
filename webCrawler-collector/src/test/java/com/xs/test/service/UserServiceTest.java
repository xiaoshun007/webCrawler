package com.xs.test.service;

import com.xs.domain.User;
import com.xs.service.IUserService;
import com.xs.test.baseTest.SpringTestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends SpringTestCase {

    @Resource
    private IUserService userService;

    @Test
    public void selectUserByIdTest(){
        User user = userService.selectUserById(1);
        System.out.println(user.getUserName() + ":" + user.getUserPassword());
    }
}
