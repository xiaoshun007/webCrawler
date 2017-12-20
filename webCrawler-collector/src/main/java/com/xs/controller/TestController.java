package com.xs.controller;

import com.xs.downloader.ZhihuFolloweePageProcessor;
import com.xs.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class TestController {

    @Resource
    private IUserService userService;

    @RequestMapping(value="/index")
    public void index() {
        System.out.println("Hello SpringMVC");


        ZhihuFolloweePageProcessor zhihuFolloweePageProcessor = new ZhihuFolloweePageProcessor();
        zhihuFolloweePageProcessor.downloadFollowees();
    }

    @RequestMapping(value="/user")
    public void getUser() {
        userService.selectUserById(1);
    }
}
