package com.githrd.demo_aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component // 이렇게 해야지 자동으로 생성
public class Advice {
    //Spring에게 Injection시켜달라고 요청
    @Autowired
    HttpServletRequest request;

    // Target(감시지점) 설정!!! 
    //   *(..) : 모든메소드(.. <-인자구분없다)
    @Pointcut("execution(* com.githrd.demo_aop.service.*Service.*(..))")
    public void service_pointcut(){}
    // 이 서비스 통과하는 모든 메소드에 대해서 포인트 컷을 건다 
    // 메서드이름은 아래 Before, After와 연결하기 위해서 


   
    @Before("service_pointcut()")
    public void before(JoinPoint jp){

        Signature s = jp.getSignature();
        System.out.println("--Before : "  + s.toShortString());

        long start = System.currentTimeMillis(); // 시작시간
        //request binding 
        request.setAttribute("start", start);

    }

    @After("service_pointcut()")
    public void after(JoinPoint jp){

        Signature s = jp.getSignature();
        System.out.println("--After : "  + s.toShortString());


        long start = (Long)request.getAttribute("start");

        long end = System.currentTimeMillis(); 


        System.out.printf("--[수행시간] : %d(ms)\n", end - start);

    }
}