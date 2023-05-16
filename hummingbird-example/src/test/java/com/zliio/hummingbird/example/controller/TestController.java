package com.zliio.hummingbird.example.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * 控制器测试
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class TestController {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8081";
    }

    @Test
    void get_user_by_id() {
        when().get("/test").
                then().
                statusCode(200).
                body("hello", equalTo("World"));

    }
}
