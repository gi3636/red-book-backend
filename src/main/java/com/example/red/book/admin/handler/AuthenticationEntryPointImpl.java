package com.example.red.book.admin.handler;

import com.alibaba.fastjson.JSON;
import com.example.red.book.admin.utils.WebUtils;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("认证失败了");
        String json = JSON.toJSONString(GlobalException.from(ResultCode.UNAUTHORIZED));
        WebUtils.renderString(response, json);
    }
}
