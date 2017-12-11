package com.xs.controller;

import com.xs.downloader.ZhihuFolloweePageProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class TestController {

    @RequestMapping(value="/index")
    public void index() {
        System.out.println("Hello SpringMVC");


        ZhihuFolloweePageProcessor zhihuFolloweePageProcessor = new ZhihuFolloweePageProcessor();
        zhihuFolloweePageProcessor.downloadFollowees();
    }
}
