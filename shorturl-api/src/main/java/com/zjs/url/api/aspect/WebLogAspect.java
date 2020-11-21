package com.zjs.url.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zjs
 */
@Aspect
@Component
public class WebLogAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            Object result = joinPoint.proceed();
            ObjectMapper objectMapper = new ObjectMapper();
            long end = System.currentTimeMillis();
            logger.info("|| {} | {} | {} | {} | {} | {} | {} | {} | {} | {}ms | {} ||",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    request.getRemoteAddr(),
                    request.getHeader("X-Real-IP"),
                    request.getHeader("X-Forwarded-For"),
                    request.getMethod(),
                    request.getRequestURL() + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()),
                    request.getHeader("user-agent"),
                    "",
                    end - start,
                    result == null ? "" : objectMapper.writeValueAsString(result)
            );
            return result;
        } catch (Throwable ex) {
            ex.printStackTrace();
            long end = System.currentTimeMillis();
            logger.info("|| {} | {} | {} | {} | {} | {} | {} | {} | {} | {}ms | {} ||",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    request.getRemoteAddr(),
                    request.getHeader("X-Real-IP"),
                    request.getHeader("X-Forwarded-For"),
                    request.getMethod(),
                    request.getRequestURL() + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()),
                    request.getHeader("user-agent"),
                    "",
                    end - start,
                    ex.toString()
            );
            throw ex;
        }
    }
}
