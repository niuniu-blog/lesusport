package com.haichuang.lesusport.common.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestUtils {
    public static String getCSESSION(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 1) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("CSESSIONID")) {
                    return cookie.getValue();
                }
            }
        }
        String toke = UUID.randomUUID().toString().replaceAll("-", "");
        Cookie ck = new Cookie("CSESSIONID", toke);
        ck.setPath("/");
        ck.setMaxAge(-1);
        response.addCookie(ck);
        return toke;
    }
}
