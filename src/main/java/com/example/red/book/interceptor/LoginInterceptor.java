package com.example.red.book.interceptor;



import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/26/2021 9:26 AM
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println("token：" + token);
        String requestURI = request.getRequestURI().replaceAll("/+", "/");
        log.info("requestURI: {}", requestURI);
        //判断是否获是swagger api的资料
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //判断如果请求的类是swagger的控制器，直接通行。
        if (handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController")) {
            return true;
        }
        ////验证token
        //if (StringUtils.isBlank(token)) {
        //    throw GlobalException.from(ResultCode.UNAUTHORIZED);
        //}
        //if (!jwtTokenUtil.validateToken(token)) {
        //    throw GlobalException.from(ResultCode.UNAUTHORIZED);
        //}
        //String id = JwtUtils.getIdByJwtToken(request);
        ////用户id 放到上下文，可以当前请求进行传递
        //System.out.println("存储：" + SessionContext.USER_ID_KEY);
        //request.setAttribute(SessionContext.USER_ID_KEY, id);
        //System.out.println("预处理成功");
        //System.out.println("id :" + id);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
