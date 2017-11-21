package com.haichuang.lesusport.interceptor;

import com.haichuang.lesusport.common.Utils.RequestUtils;
import com.haichuang.lesusport.service.login.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));
        if (null != username) {
            return true;
        }
        response.sendRedirect("http://localhost:8082/login.aspx?returnUrl=http://localhost:8083/");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
