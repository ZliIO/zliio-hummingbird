package com.zliio.hummingbird.example.injection.impl;

import com.zliio.hummingbird.core.annotation.ioc.Autowired;
import com.zliio.hummingbird.core.annotation.ioc.Component;
import com.zliio.hummingbird.example.injection.CyclicInjectionClassA;
import com.zliio.hummingbird.example.injection.CyclicInjectionClassC;

/**
 * CyclicInjectionClassCImpl
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/

@Component
public class CyclicInjectionClassCImpl implements CyclicInjectionClassC {

    @Autowired
    private CyclicInjectionClassA cyclicInjectionClassA;

    @Override
    public void printClassName() {

    }
}
