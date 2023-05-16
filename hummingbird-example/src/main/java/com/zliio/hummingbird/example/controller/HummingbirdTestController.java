package com.zliio.hummingbird.example.controller;

import com.zliio.hummingbird.core.annotation.ioc.Autowired;
import com.zliio.hummingbird.example.bean.UserReq;
import com.zliio.hummingbird.example.bean.UserRsp;
import com.zliio.hummingbird.example.injection.CyclicInjectionClassA;
import com.zliio.hummingbird.example.service.HummingbirdTestService;
import com.zliio.hummingbird.web.annotation.*;
import com.zliio.hummingbird.web.common.http.DefaultHttpRsp;
import com.zliio.hummingbird.web.common.http.ServiceResponseMessage;
import com.zliio.hummingbird.web.common.lang.HttpMediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class HummingbirdTestController {

    @Autowired
    private HummingbirdTestService hummingbirdTestService;

    @Autowired
    private CyclicInjectionClassA cyclicInjectionClassA;

    @GetMapping
    public ServiceResponseMessage<DefaultHttpRsp> hello(){
        hummingbirdTestService.sayHello();
        return ServiceResponseMessage.createBySuccessCodeMessage();
    }




    @GetMapping(path = "/user/{name}",produces = HttpMediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServiceResponseMessage<UserRsp> printHelloWithAge(@RequestParam("age") Integer age, @PathVariable("name")String name){
        UserRsp userRsp = new UserRsp();
        userRsp.setAge(age);
        userRsp.setName(name);
        userRsp.setDescription(hummingbirdTestService.base64EncodeName(name));
        return ServiceResponseMessage.createBySuccessCodeMessage(userRsp);
    }

    @PostMapping(path = "/user",consumes = HttpMediaType.APPLICATION_JSON_UTF8_VALUE,produces = HttpMediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServiceResponseMessage<UserRsp> printRequest(@RequestBody UserReq userReq){
        UserRsp userRsp = new UserRsp();
        userRsp.setAge(userReq.getAge() + 1);
        userRsp.setName(userReq.getName() + "-Name");
        userRsp.setDescription(userReq.getDescription() + UUID.randomUUID().toString());
        return ServiceResponseMessage.createBySuccessCodeMessage(userRsp);
    }

    @GetMapping(path = "/test",produces = HttpMediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String,String> printRequest1(){
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("hello", "World");
        return map;
    }


    @PutMapping(path = "/user",consumes = HttpMediaType.APPLICATION_JSON_UTF8_VALUE,produces = HttpMediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServiceResponseMessage<UserRsp> printRequesto(@RequestBody UserReq userReq){
        UserRsp userRsp = new UserRsp();
        userRsp.setAge(userReq.getAge() + 1);
        userRsp.setName(userReq.getName() + "-Name");
        userRsp.setDescription(userReq.getDescription() + UUID.randomUUID().toString());
        return ServiceResponseMessage.createBySuccessCodeMessage(userRsp);
    }

    @DeleteMapping(path = "/user",consumes = HttpMediaType.APPLICATION_JSON_UTF8_VALUE,produces = HttpMediaType.APPLICATION_JSON_UTF8_VALUE)
    public ServiceResponseMessage<UserRsp> printRequestoo(@RequestBody UserReq userReq){
        UserRsp userRsp = new UserRsp();
        userRsp.setAge(userReq.getAge() + 1);
        userRsp.setName(userReq.getName() + "-Name");
        userRsp.setDescription(userReq.getDescription() + UUID.randomUUID().toString());
        return ServiceResponseMessage.createBySuccessCodeMessage(userRsp);
    }

}
