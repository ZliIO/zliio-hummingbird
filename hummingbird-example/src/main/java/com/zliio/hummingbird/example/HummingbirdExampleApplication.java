package com.zliio.hummingbird.example;

import com.zliio.hummingbird.web.WebApplicationContext;

public class HummingbirdExampleApplication {
    public static void main(String[] args) {
        WebApplicationContext.getWebApplicationContext().run(HummingbirdExampleApplication.class,args);
    }
}
