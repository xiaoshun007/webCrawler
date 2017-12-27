package com.xs.dubbo.service.impl;

import com.xs.dubbo.service.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public String getName() {
        return "hello dubbo!";
    }
}
