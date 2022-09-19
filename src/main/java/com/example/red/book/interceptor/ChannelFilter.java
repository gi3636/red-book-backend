package com.example.red.book.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author wangyan
 * @description 自定义过滤器
 * @date 2019/7/19 18:26
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "channelFilter")
public class ChannelFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(ChannelFilter.class);

    @Autowired
    MultipartResolver multipartResolver;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("================ 进入过滤器 ======================");

        String contentType = servletRequest.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")) {
            MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart((HttpServletRequest) servletRequest);
            filterChain.doFilter(multipartRequest, servletResponse);
            return;
        }
        if (contentType != null && contentType.contains("x-www-form-urlencoded")) {
            ResourceUrlEncodingFilter filter = new ResourceUrlEncodingFilter();
            filter.doFilter(servletRequest, servletResponse, filterChain);
            return;
        }

        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new RequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
