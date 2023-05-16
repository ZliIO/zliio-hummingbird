package com.zliio.hummingbird.example.bean;


import com.zliio.hummingbird.web.common.http.HttpResponse;

/**
 * 用户请求回包
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class UserRsp implements HttpResponse {
    private String name;
    private Integer age;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
