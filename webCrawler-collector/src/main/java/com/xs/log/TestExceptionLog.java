package com.xs.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class TestExceptionLog {
    private static Log logger = LogFactory.getLog(TestExceptionLog.class);

    @SystemServiceLog(description = "equalstr")
    public String equalStr(String str) {
        str = null;
        if (str.equals("sd")) {
            return "sd";
        }else {
            return "sd";
        }
    }
}