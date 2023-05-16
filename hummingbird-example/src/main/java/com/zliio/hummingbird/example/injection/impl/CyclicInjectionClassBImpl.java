package com.zliio.hummingbird.example.injection.impl;

import com.zliio.hummingbird.core.annotation.ioc.Autowired;
import com.zliio.hummingbird.core.annotation.ioc.Component;
import com.zliio.hummingbird.example.injection.CyclicInjectionClassB;
import com.zliio.hummingbird.example.injection.CyclicInjectionClassC;

/**
 * CyclicInjectionClassBImpl
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/

@Component
public class CyclicInjectionClassBImpl implements CyclicInjectionClassB {

    @Autowired
    private CyclicInjectionClassC cyclicInjectionClassC;

    @Override
    public void printClassName() {

    }
}
