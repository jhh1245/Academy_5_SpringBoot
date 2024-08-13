package com.githrd.demo_aop.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    @Override
    public String hello() {

        try {
            Thread.sleep(1234); // 수행시간
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return "Hello Everyone!!";
    }

    @Override
    public String hi() {

        for(int i=0; i<1000000000L; i++);
        
        return "Hi~ Thank you!";

    }

}
