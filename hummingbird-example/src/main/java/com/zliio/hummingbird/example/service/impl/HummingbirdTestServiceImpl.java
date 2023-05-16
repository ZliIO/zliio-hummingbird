package com.zliio.hummingbird.example.service.impl;

import com.zliio.hummingbird.core.annotation.ioc.Component;
import com.zliio.hummingbird.core.annotation.ioc.Value;
import com.zliio.hummingbird.example.service.HummingbirdTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * @author ZLiIO
 */
@Component
public class HummingbirdTestServiceImpl implements HummingbirdTestService {
    private final static Logger loger = LoggerFactory.getLogger(HummingbirdTestService.class);

    @Value("test.age")
    private Integer age;

    @Override
    public void sayHello() {
        System.out.println("Hello Hummingbird");
    }

    @Override
    public String base64EncodeName(String name) {
        loger.warn("base64EncodeName Hello Hummingbird Run ...");
        loger.warn("Value Age    >>>>>>>   " + age);
        return Base64.getEncoder().encodeToString(name.getBytes());
    }
}
