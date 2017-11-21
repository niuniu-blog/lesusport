package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("test")
    public String test() {
        int i = testService.getInt();
        System.out.println(i);
        return "test";
    }
}
