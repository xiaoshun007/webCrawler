package com.xs.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {
    private static Log logger = LogFactory.getLog(LogController.class);
    //自动注入一个Service层类对象
    @Autowired
    TestExceptionLog testExceptionLog;

    @ResponseBody
    @RequestMapping("/admin/testexcption")
    @SystemControllerLog(description = "testException")//使用   SystemControllerLog注解，此为切点
    public String testException(String str){
        return testExceptionLog.equalStr(str);
    }

}
