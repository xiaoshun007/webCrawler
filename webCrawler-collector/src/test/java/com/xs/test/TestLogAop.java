package com.xs.test;

import com.xs.log.LogController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestLogAop {
    @Test
    public void testAOP1(){
        //启动Spring容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-mvc.xml"});
        //获取service或controller组件
        LogController userController = (LogController) ctx.getBean("logController");
        userController.testException("11");
    }
}
