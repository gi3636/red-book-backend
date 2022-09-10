package com.example.red.book.interceptor;


import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/26/2021 9:26 AM
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 将请求参数转化为Map
     *
     * @param request
     * @return
     */
    public static void printLog(HttpServletRequest request) {
        try {
            //获取请求参数
            RequestWrapper requestWrapper = new RequestWrapper(request);
            log.info("入参： {}", requestWrapper.getBodyString());
        } catch (Exception e) {
            log.error("HttpInterceptor preHandle error", e);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI().replaceAll("/+", "/");
        log.info("请求方法与链接: {}  {}", request.getMethod(), requestURI);
        printLog(request);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //有注解就直接放行
        WhiteList methodAnnotation = handlerMethod.getMethodAnnotation(WhiteList.class);
        if (Objects.nonNull(methodAnnotation)) {
            return true;
        }
        Class<?> clazz = handlerMethod.getBeanType();
        if (Objects.nonNull(AnnotationUtils.findAnnotation(clazz, WhiteList.class))) {
            return true;
        }
        String token = request.getHeader("token");
        //判断是否获是swagger api的资料
        //判断如果请求的类是swagger的控制器，直接通行。
        if (handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController")) {
            return true;
        }
        ////验证token
        if (StringUtils.isBlank(token)) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        if (!jwtTokenUtil.validateToken(token)) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        String username = jwtTokenUtil.getUserNameFromToken(token);
        log.info("用户：{}, token:{}", username, token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        log.info("================ 请求完成 ======================");
    }

}
