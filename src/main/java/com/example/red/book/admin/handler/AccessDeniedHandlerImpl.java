package com.example.red.book.admin.handler;

import com.alibaba.fastjson.JSON;
import com.example.red.book.admin.utils.WebUtils;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("认证失败了");
        String json = JSON.toJSONString(CommonResult.failed(ResultCode.FORBIDDEN));
        WebUtils.renderString(response, json);
    }
}
