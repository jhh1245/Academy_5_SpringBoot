package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class HomeController {

    @RequestMapping(value="/hello.do", method=RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "안녕하세요 SpringBoot!!";
    }
    
    @RequestMapping("/hi.do")
    public String hi(Model model){ // 메소드 인자 = DS에게 요구할 사항

        String name = "홍길동";
        
        model.addAttribute("name", name);

        return "hi"; // /templates/ + hi + .html
    }

    @RequestMapping(value="/bye.do")
    public String bye(@RequestParam(name="name") String name, Model model){ // 메소드 인자 = DS에게 요구할 사항
        model.addAttribute("name", name);

        return "bye"; // /templates/ + bye + .html
    }
}
