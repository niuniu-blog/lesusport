package com.haichuang.lesusport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 返回子页面的控制器
 */
@Controller
@RequestMapping("frame")
public class FrameController {
    /**
     * 前往商品主页面
     *
     * @return
     */
    @RequestMapping("product_main")
    public String product_main() {
        return "frame/product_main";
    }

    /**
     * 前往订单主页面
     *
     * @return
     */
    @RequestMapping("order_main")
    public String order_main() {
        return "frame/order_main";
    }

    /**
     * 前往广告主页面
     *
     * @return
     */
    @RequestMapping("ad_main")
    public String ad_main() {
        return "frame/ad_main";
    }

    /**
     * 商品菜单
     *
     * @return
     */
    @RequestMapping("product_left")
    public String product_left() {
        return "frame/product_left";
    }

    /**
     * 订单菜单
     *
     * @return
     */
    @RequestMapping("order_left")
    public String order_left() {
        return "frame/order_left";
    }

    /**
     * 订单列表
     *
     * @return
     */
    @RequestMapping("order/list")
    public String order_list() {
        return "order/list";
    }

    /**
     * 广告菜单
     *
     * @return
     */
    @RequestMapping("ad_left")
    public String ad_left() {
        return "frame/ad_left";
    }

    /**
     * 广告管理
     *
     * @return
     */
    @RequestMapping("position/list")
    public String position_list() {
        return "position/list";
    }

    /**
     * 类型管理
     *
     * @return
     */
    @RequestMapping("type/list")
    public String typeList() {
        return "type/list";
    }

}
