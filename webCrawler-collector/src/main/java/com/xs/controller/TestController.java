package com.xs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
    @RequestMapping(value="/index")
    public void index() {
        System.out.println("Hello SpringMVC");
    }
}
