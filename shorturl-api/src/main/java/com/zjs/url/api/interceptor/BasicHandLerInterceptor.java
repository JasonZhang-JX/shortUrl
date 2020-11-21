package com.zjs.url.api.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 基础拦截器
 *
 * @author zjs
 */
public class BasicHandLerInterceptor implements HandlerInterceptor {

    public boolean preHandleBlack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //todo 如需登录需补充相关逻辑
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
