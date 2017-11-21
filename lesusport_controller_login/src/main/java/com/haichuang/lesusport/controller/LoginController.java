package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.common.Utils.RequestUtils;
import com.haichuang.lesusport.pojo.user.Buyer;
import com.haichuang.lesusport.service.login.BuyerService;
import com.haichuang.lesusport.service.login.SessionProvider;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SessionProvider sessionProvider;

    @RequestMapping("isLogin.aspx")
    public @ResponseBody
    MappingJacksonValue login(String callback, HttpServletRequest request, HttpServletResponse response) {
        String res = "0";
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));
        if (username != null) {
            res = "1";
        }
        MappingJacksonValue mjv = new MappingJacksonValue(res);
        mjv.setJsonpFunction(callback);
        return mjv;
    }

    /**
     * 登录
     *
     * @param username
     * @param returnUrl
     * @param password
     * @param model
     * @param request
     * @param respon
     * @return
     */
    @RequestMapping("login.aspx")
    public String login(String username, String returnUrl, String password, Model model, HttpServletRequest request, HttpServletResponse respon) {
        if (null != username) {
            if (null != password) {
                Buyer buyer = buyerService.getBuyerByUsername(username);
                if (buyer != null) {
                    String s = encodePassword(password);
                    if (s.equals(buyer.getPassword())) {
                        //登录成功,放入远程session，使用cookie生成令牌
                        //cookie 在浏览器本地保存 用来在redis中查找键，以便获取用户名
                        sessionProvider.setAttributeForUsername(RequestUtils.getCSESSION(request, respon), username);
                        return "redirect:" + returnUrl;
                    } else {
                        model.addAttribute("error", "请输入正确的密码");
                    }
                } else {
                    model.addAttribute("error", "请输入正确的用户名");
                }
            } else {
                model.addAttribute("error", "密码不能为空");
            }

        } else {
            model.addAttribute("error", "用户名不能为空!");
        }
        return "login";
    }

    private String encodePassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            char[] chars = Hex.encodeHex(bytes);
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
