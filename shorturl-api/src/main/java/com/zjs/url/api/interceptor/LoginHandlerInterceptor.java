package com.zjs.url.api.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**登录拦截器
 * @author zjs
 */
@Component
public class LoginHandlerInterceptor extends BasicHandLerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.preHandleBlack(request,response);
    }
}