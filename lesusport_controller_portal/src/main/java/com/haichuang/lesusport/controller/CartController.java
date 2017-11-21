package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.common.Utils.JsonUtils;
import com.haichuang.lesusport.common.Utils.RequestUtils;
import com.haichuang.lesusport.constans.Constans;
import com.haichuang.lesusport.pojo.buyer.BuyerCart;
import com.haichuang.lesusport.pojo.buyer.BuyerItem;
import com.haichuang.lesusport.service.login.BuyerService;
import com.haichuang.lesusport.service.login.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("shopping")
public class CartController {
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private BuyerService buyerService;

    @RequestMapping("/buyerCart/{skuId}/{amount}")
    public String buyerCart(@PathVariable Long skuId, @PathVariable Integer amount, HttpServletRequest request, HttpServletResponse response) {
        BuyerCart cart = null;
        //取出cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //如果存在购物车
            if (cookie.getName().equals(Constans.BUYER_CART)) {
                //获取cookie中的购物车
                String cartJson = cookie.getValue();
                cart = JsonUtils.jsonToObject(cartJson, BuyerCart.class);
                break;
            }
        }
        if (cart == null) {
            cart = new BuyerCart();
        }
        //追加购物车
        if (null != skuId && null != amount) {
            cart.addItem(skuId, amount);
        }

        //判断是否登录了
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));
        if (null != username) {
            buyerService.saveBuyerToRedis(cart, username);
            //登录了,清除cookie
            Cookie ck = new Cookie(Constans.BUYER_CART, null);
            ck.setMaxAge(0);
            ck.setPath("/");
            response.addCookie(ck);
        } else {
            //未登录
            Cookie ck = new Cookie(Constans.BUYER_CART, JsonUtils.ObjectToJson(cart));
            ck.setMaxAge(60 * 60 * 24 * 7);
            ck.setPath("/");
            response.addCookie(ck);
        }
        return "redirect:/shopping/toCart";
    }

    @RequestMapping("toCart")
    public String toCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        BuyerCart cart = null;
        //取出cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //如果存在购物车
            if (cookie.getName().equals(Constans.BUYER_CART)) {
                //获取cookie中的购物车
                String cartJson = cookie.getValue();
                cart = JsonUtils.jsonToObject(cartJson, BuyerCart.class);
                break;
            }
        }
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));
        if (null != username) {
            //用户登录了
            if (cart != null) {
                //合并cookie中的购物车
                buyerService.saveBuyerToRedis(cart, username);
                //登录了,清除cookie
                Cookie ck = new Cookie(Constans.BUYER_CART, null);
                ck.setMaxAge(0);
                ck.setPath("/");
                response.addCookie(ck);
            }
            //获取redis中的购物车
            cart = buyerService.getBuyerCartFromRedis(username);
        }
        if (null != cart) {
            for (BuyerItem item : cart.getItems()) {
                //查找商品对象，颜色对象，sku对象
                item.setSku(buyerService.getSkuBySkuId(item.getSku().getId()));
            }
        }
        model.addAttribute("buyerCart", cart);
        return "cart";
    }
}
