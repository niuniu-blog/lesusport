package com.haichuang.lesusport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转页面
 */
@Controller
@RequestMapping("center")
public class CenterController {
    /**
     * index页面入口
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    /**
     * 返回top页面
     */
    @RequestMapping("top")
    public String top() {
        return "top";
    }

    /**
     * @return 返回main页面
     */
    @RequestMapping("main")
    public String main() {
        return "main";
    }

    /**
     * @return 返回left页面
     */
    @RequestMapping("left")
    public String left() {
        return "left";
    }

    /**
     * @return 返回right页面
     */
    @RequestMapping("right")
    public String right() {
        return "right";
    }
}
