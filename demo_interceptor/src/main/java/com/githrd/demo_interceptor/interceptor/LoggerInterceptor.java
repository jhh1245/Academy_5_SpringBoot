package com.githrd.demo_interceptor.interceptor;

import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j //@Slf4j <= Lombok설치하면 Log처리객체 자동처리
public class LoggerInterceptor implements HandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        
        // 로그인 정보 얻어오기 
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("user");

        if(user == null){ // 로그인 X 
            response.sendRedirect("../myerror?reason=session_timeout");
            return false;
        }

        String uri = request.getRequestURI();
        // uri = /admin/members
        // uri = /adult/....

        if(uri.startsWith("/admin/")){ // 관리자 페이지? 
            
            String grade = (String) user.get("grade"); // user 세션에서 grade를 가져온다

            if(!grade.equals("admin")) { // 관리자가 아니면 
                
                response.sendRedirect("../myerror?reason=not_admin");
                return false;

            }

        } else if(uri.startsWith("/adult/")){ // 성인만 이용 가능한 경로
 
            int age = Integer.parseInt((String) user.get("age"));
            
            if(age < 20){
                response.sendRedirect("../myerror?reason=not_adult"); 
                return false;
            }
        }


        // log.info("===============================================");
        // log.info("==================== BEGIN ====================");
        System.out.println("==================== BEGIN ====================");
        System.out.println("Request URI ===> " + request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("==================== END ======================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}


