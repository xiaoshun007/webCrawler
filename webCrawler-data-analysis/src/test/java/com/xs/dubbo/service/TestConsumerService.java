package com.xs.dubbo.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestConsumerService {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "application.xml" });
        context.start();
        TestService testService = (TestService) context.getBean("testService");
        System.out.println(testService.getName());
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
